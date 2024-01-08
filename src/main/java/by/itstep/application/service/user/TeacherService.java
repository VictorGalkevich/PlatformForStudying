package by.itstep.application.service.user;

import by.itstep.application.entity.*;
import by.itstep.application.entity.type.StatusType;
import by.itstep.application.repository.*;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final GetEntity getEntity;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TestRepository testRepository;
    private final AssignmentRepository assignmentRepository;

    public void registerTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public ApiResponse<String> assignTestForStudent(User user, Integer idTest, String groupName) {

        try {
            getEntity.getTeacherForUser(user);
        } catch (Exception e) {
            return ApiResponse.error("sorry you are not a teacher");
        }

        var group = getEntity.getGroupByName(groupName);
        var test = getEntity.getTestById(idTest);

        List<Student> students = group.getStudents();
        students.forEach(student -> student.setTest(test));
        studentRepository.saveAll(students);

        testRepository.save(test);

        return ApiResponse.success("Test added for students in the group " + groupName);
    }

    public ApiResponse<String> sendResultTest(User user, Integer rating, Integer idAssigment) {
        var teacher = getEntity.getTeacherForUser(user);
        var assignment = getEntity.getAssignmentById(idAssigment);
        if (!assignment.getTest().getCreateBy().equals(teacher.getUser().getFirstname() + " " +
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


}

