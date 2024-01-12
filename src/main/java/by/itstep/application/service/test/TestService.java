package by.itstep.application.service.test;

import by.itstep.application.entity.Assignment;
import by.itstep.application.entity.Question;
import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import by.itstep.application.entity.type.StatusType;
import by.itstep.application.repository.*;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final StudentRepository studentRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final GetEntity getEntity;

   // @PreAuthorize("hasAnyAuthority('ADMIN') or hasAnyAuthority('MODERATOR')")
    @Transactional
    public ApiResponse<String> createTest(User user, final List<Question> questions) {
        var teacher = getEntity.getTeacherForUser(user);
        try {
            if (questions.isEmpty()) {
                return ApiResponse.error("Cannot create a test without questions");
            }
            Test test = new Test();
            questionRepository.saveAll(questions);
            test.setCreateBy(user.getFirstname() + " " + user.getLastname());
            test.setQuestions(questions);
            testRepository.save(test);

            teacher.addTestForTeacher(test);
            teacherRepository.save(teacher);

            return ApiResponse.success("Test saved");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while creating the test");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTest(Integer id) {
        try {
            Test test = getEntity.getTestById(id);

            if (test == null) {
                return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
            }

            if (!test.isTestAccessible()) {
                return new ResponseEntity<>("Test is not currently accessible", HttpStatus.FORBIDDEN);
            }
            if (test.getStartTime() == null || test.getEndTime() == null) {
                return ResponseEntity.ok("Please add timings for the test");
            }
            LocalDateTime now = LocalDateTime.now();

            if (test.getStartTime() != null && now.isBefore(test.getStartTime())) {
                return ResponseEntity.ok("Test is not yet available");
            }

            if (test.getEndTime() != null && now.isAfter(test.getEndTime())) {
                return ResponseEntity.ok("Test is no longer available");
            }

            if (test.getDuration() != null) {
                Duration testDuration = Duration.ofMinutes(test.getDuration());
                LocalDateTime endTimeForStudent = now.plus(testDuration);
                test.setEndTime(endTimeForStudent);
            }
            return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching the test", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  //  @PreAuthorize("hasAnyAuthority('ADMIN') or hasAnyAuthority('MODERATOR')")
    public ApiResponse<String> setTimingsForTest(TestTimeRequest testRequest) {
        try {
            Optional<Test> optionalTest = testRepository.findByIdWithQuestions(testRequest.getIdTest());

            if (optionalTest.isEmpty()) {
                return ApiResponse.error("Test not found with Id: " + testRequest.getIdTest());
            }

            Test test = optionalTest.get();
            test.setStartTime(testRequest.getStartTime());
            test.setEndTime(testRequest.getEndTime());
            test.setDuration(testRequest.getDuration());

            testRepository.save(test);

            return ApiResponse.success("Time is set");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while setting timings for the test");
        }
    }

    public ApiResponse<String> passTest(User user) {
        try {
            var student = getEntity.getStudentForUser(user);

            if (student.getTest() == null) {
                return ApiResponse.error("this student didn't get test");
            }

            Assignment assignment = new Assignment();
            assignment.setStatus(StatusType.SEND_FOR_CHECK);
            assignment.setTest(student.getTest());
            student.addAssigment(assignment);
            student.setTest(null);

            studentRepository.save(student);

            // ToDo request for user answer
            // ToDo assigment save() -- duplicate

            return ApiResponse.success("Test passed");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while passing the test");
        }
    }

}