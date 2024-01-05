package by.itstep.application.service.user;

import by.itstep.application.entity.*;
import by.itstep.application.repository.GroupRepository;
import by.itstep.application.repository.StudentRepository;
import by.itstep.application.repository.TeacherRepository;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    public void registerTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public ApiResponse<String> createGroup(User user, String groupName) {
        var response = new ApiResponse<String>();
        var optionalGroup = groupRepository.findByName(groupName);
        if (optionalGroup.isPresent()) {
            response = ApiResponse.error("this is name for group already exist, " +
                                         "think about another name, for example " + groupName + " + subject name");
        } else {
            var group = new Group();
            var teacher = teacherRepository.findByUser(user).get();
            group.setName(groupName);
            teacher.addGroupForTeacher(group);
            groupRepository.save(group);
            teacherRepository.save(teacher);
            response = ApiResponse.success("successful add group");
        }
        return response;
    }
    public ApiResponse<String> addStudentForGroup(User user,
                                                  Integer idStudent,
                                                  String groupName) {
        var response = new ApiResponse<String>();
        var teacher = teacherRepository.findByUser(user).get();
        var optionalGroup = groupRepository.findByName(groupName);
        if (optionalGroup.isEmpty()) {
            response = ApiResponse.error("please create the group with name: " + groupName);
        } else if (teacher.getGroups().isEmpty()) {
            response = ApiResponse.error("this is teacher hasn't groups");
        } else {
            var student = studentRepository.findById(idStudent).get();
            var group = optionalGroup.get();
            if (!teacher.getGroups().contains(group)){
                return ApiResponse.error("no such groups for this is teacher");
            }
            if (group.getStudents().contains(student)) {
                return ApiResponse.error("this is student already add in the group");
            }
            group.addStudentsForGroup(student);
            studentRepository.save(student);
            groupRepository.save(group);
            teacherRepository.save(teacher);
            response = ApiResponse.success("success add student for the group");
        }
        return response;
    }

}

