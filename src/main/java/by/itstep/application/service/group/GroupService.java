package by.itstep.application.service.group;

import by.itstep.application.entity.Group;
import by.itstep.application.entity.Student;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import by.itstep.application.repository.GroupRepository;
import by.itstep.application.repository.StudentRepository;
import by.itstep.application.repository.TeacherRepository;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final GetEntity getEntity;
    private final StudentRepository studentRepository;

    public ApiResponse<String> createGroup(User user, String groupName) {
        ApiResponse<String> response;
        if (groupRepository.findByName(groupName).isPresent()) {
            response = ApiResponse.error("This group name already exists. Please choose another name, for example: " + groupName + " + subject name");
        } else {
            try {
                Group group = new Group();
                group.setName(groupName);

                Teacher teacher = teacherRepository.findByUser(user).orElseThrow(() -> new IllegalStateException("Teacher not found for user: " + user.getId()));
                teacher.addGroupForTeacher(group);

                groupRepository.save(group);
                teacherRepository.save(teacher);

                response = ApiResponse.success("Successfully added group");
            } catch (Exception e) {
                response = ApiResponse.error("An error occurred while creating the group");
            }
        }

        return response;
    }

    public ApiResponse<String> addStudentForGroup(User user, Integer idStudent, String groupName) {
        try {
            Teacher teacher = getEntity.getTeacherForUser(user);
            Group group = getEntity.getGroupByName(groupName);
            List<Group> groups = teacherRepository.findGroupsByTeacherId(teacher.getId());
            if (!groups.contains(group)) {
                return ApiResponse.error("There are no such groups for this teacher.");
            }

            Student student = getEntity.getStudentById(idStudent);

            if (group.getStudents().contains(student)) {
                return ApiResponse.error("This student has already been added to the group");
            }

            group.addStudentsForGroup(student);
            studentRepository.save(student);
            groupRepository.save(group);
            teacherRepository.save(teacher);

            return ApiResponse.success("Successfully added the student to the group");
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while adding the student to the group");
        }
    }
}
