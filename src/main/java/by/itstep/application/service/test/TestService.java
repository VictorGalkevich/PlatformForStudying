package by.itstep.application.service.test;

import by.itstep.application.entity.Assignment;
import by.itstep.application.entity.Question;
import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import by.itstep.application.entity.type.StatusType;
import by.itstep.application.rest.controller.request.TestTimeRequest;
import by.itstep.application.rest.dto.TestDTO;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {
    private final GetEntity getEntity;

    @Transactional
    public ApiResponse<String> createTest(User user, Set<Question> questions, String testName) {
        var teacher = getEntity.getTeacherForUser(user);
        try {
            if (questions == null || questions.isEmpty()) {
                return ApiResponse.error("Cannot create a test without questions");
            }

            getEntity.saveQuestions(questions);

            var test = new Test();
            test.setTestName(testName);
            test.setCreatedBy(user.getFirstname() + " " + user.getLastname());
            test.setQuestions(questions);
            getEntity.saveTest(test);

            teacher.addTestForTeacher(test);
            getEntity.saveTeacher(teacher);

            return ApiResponse.success("Test saved");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while creating the test");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTest(Long id) {
        try {
            var test = getEntity.getTestById(id);

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

            return new ResponseEntity<>(test, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching the test", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ApiResponse<String> setTimingsForTest(TestTimeRequest testRequest) {
        try {
            Optional<Test> optionalTest = getEntity.findByIdWithQuestions(testRequest.getIdTest());

            if (optionalTest.isEmpty()) {
                return ApiResponse.error("Test not found with Id: " + testRequest.getIdTest());
            }

            var test = optionalTest.get();
            test.setStartTime(testRequest.getStartTime());
            test.setEndTime(testRequest.getEndTime());
            test.setDuration(testRequest.getDuration());

            getEntity.saveTest(test);

            return ApiResponse.success("Time is set");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while setting timings for the test");
        }
    }

    @Transactional
    public ApiResponse<String> passTest(User user, List<String> userAnswers, Long testId) {

        var student = getEntity.getStudentForUser(user);
        var test = getEntity.getTestById(testId);
        for (Assignment assignment : student.getAssignments()) {
            if (assignment.getTest().equals(test)) {
                return ApiResponse.error("test already passed");
            }
        }
        var assignment = new Assignment();
        assignment.setStatus(StatusType.SEND_FOR_CHECK);
        assignment.setUserAnswers(userAnswers);

        assignment.setTest(test);

        student.addAssignment(assignment);
        student.removeTest(test);

        getEntity.saveAssignment(assignment);

        return ApiResponse.success("Test passed");
    }

    @Transactional(readOnly = true)
    public List<Test> getAllTestsWithDetails(User user) {
        return getEntity.findAllTestsByTeacherId(user.getId());
    }

    @Transactional(readOnly = true)
    public Set<TestDTO> getAllTestsForStudent(User user) {
        var tests = getEntity.getStudentForUser(user).getTests();
        return tests.stream()
                .map(TestDTO::fromTest)
                .collect(Collectors.toSet());
    }

    public void closeTest(Long idTest) {
        var test = getEntity.getTestById(idTest);
        test.setAccess(false);
        getEntity.saveTest(test);
    }

    public void removeTest(Long idTest) {
        var test = getEntity.getTestById(idTest);
        getEntity.removeTest(test);
    }

}