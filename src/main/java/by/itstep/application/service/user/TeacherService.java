package by.itstep.application.service.user;

import by.itstep.application.entity.*;
import by.itstep.application.entity.type.StatusType;
import by.itstep.application.repository.*;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final GetEntity getEntity;
    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;

    public void registerTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Transactional
    public ApiResponse<String> assignTestForGroup(User user, Integer idTest, Integer idGroup) {
        try {
             getEntity.getTeacherForUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("Sorry, you are not a teacher");
        }

        var group = getEntity.getGroupById(idGroup);
        var test = getEntity.getTestById(idTest);

        List<Student> students = group.getStudents();
        if (students.stream().anyMatch(student -> student.getTests().contains(test))) {
            return ApiResponse.error("This test is already assigned to at least one student in the group");
        }
        students.forEach(student -> {
            if (!student.getTests().contains(test)) {
                student.addTest(test);
            }
        });
        studentRepository.saveAll(students);
        return ApiResponse.success("Test added for students in the group " + group.getName());
    }

    public ApiResponse<String> sendResultTest(User user, Integer rating, Integer idAssigment) {
        var teacher = getEntity.getTeacherForUser(user);
        var assignment = getEntity.getAssignmentById(idAssigment);
        if (!assignment.getTest().getCreatedBy().equals(teacher.getUser().getFirstname() + " " +
                                                       teacher.getUser().getLastname())) {
            return ApiResponse.error("sorry, but you can't check this test");
        }
        if (assignment.getStatus() == StatusType.CHECKED) {
            return ApiResponse.error("this assigment was check");
        }
        assignment.setStatus(StatusType.CHECKED);
        assignment.setRating(rating);
        assignmentRepository.save(assignment);
        return ApiResponse.success("result was send");
    }

    @Transactional(readOnly = true)
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}

