package by.itstep.application.service.group;

import by.itstep.application.entity.*;
import by.itstep.application.repository.GroupRepository;
import by.itstep.application.repository.StudentRepository;
import by.itstep.application.repository.TeacherRepository;
import by.itstep.application.rest.response.GroupResponse;
import by.itstep.application.rest.dto.GroupWithStudentsDto;
import by.itstep.application.rest.dto.StudentDto;
import by.itstep.application.util.ApiResponse;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

                Teacher teacher = getEntity.getTeacherForUser(user);

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

    @Transactional
    public ApiResponse<String> addStudentToGroup(Integer groupId, Integer studentId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));

        Student student = getEntity.getStudentById(studentId);
        if (group.getStudents().contains(student)) {
            return ApiResponse.error("This student has already been added to the group");
        }
        group.addStudentsForGroup(student);

        groupRepository.save(group);
        studentRepository.save(student);
        return ApiResponse.success("Successfully added the student to the group");
    }

    @Transactional(readOnly = true)
    public ApiResponse<GroupWithStudentsDto> getGroupWithStudents(Integer groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        return groupOptional.map(group -> {
            Set<StudentDto> studentDtos = group.getStudents().stream()
                    .map(student -> new StudentDto(student.getId(), student.getUser().getFirstname(), student.getUser().getLastname()))
                    .collect(Collectors.toSet());
            GroupWithStudentsDto resultDto = new GroupWithStudentsDto(group, studentDtos);
            return ApiResponse.success(resultDto);
        }).orElseThrow();
    }

    public ApiResponse<List<GroupResponse>> getAllGroupResponses() {
        try {
            List<Group> groups = groupRepository.findAll();
            List<GroupResponse> groupResponses = new ArrayList<>();

            for (Group group : groups) {
                GroupResponse groupResponse = new GroupResponse();
                groupResponse.setId(group.getId());
                groupResponse.setName(group.getName());
                groupResponses.add(groupResponse);
            }

            return ApiResponse.success(groupResponses);
        } catch (Exception e) {
            return ApiResponse.error(null);
        }
    }
}
