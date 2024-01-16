package by.itstep.application.util;

import by.itstep.application.entity.*;
import by.itstep.application.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetEntity {
    private final TestRepository testRepository;
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;

    public Test getTestById(Integer idTest) {
        return testRepository.findById(idTest)
                .orElseThrow(() -> new IllegalArgumentException("Test not found with ID: " + idTest));
    }
    public Teacher getTeacherForUser(User user) {
        return teacherRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
    }
    public Group getGroupByName(String groupName) {
        return groupRepository.findByName(groupName)
                .orElseThrow(() -> new IllegalArgumentException("Group with name " + groupName + " not found"));
    }

    public Group getGroupById(Integer idGroup) {
        return groupRepository.findById(idGroup)
                .orElseThrow(() -> new IllegalArgumentException("Group with id " + idGroup + " not found"));
    }
    public Student getStudentById(Integer idStudent) {
        return studentRepository.findById(idStudent)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + idStudent));
    }
    public Student getStudentForUser(User user) {
        return studentRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
    }
    public Assignment getAssignmentById(Integer idAssignment) {
        return assignmentRepository.findById(idAssignment)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found with ID: " + idAssignment));
    }
}
