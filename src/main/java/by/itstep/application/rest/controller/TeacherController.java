package by.itstep.application.rest.controller;

import by.itstep.application.entity.Student;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import by.itstep.application.service.user.TeacherService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    @PostMapping("/create")
    public ApiResponse<String> createGroup(@AuthenticationPrincipal User user, @RequestParam("groupName") String groupName){
        return teacherService.createGroup(user, groupName);
    }
    @PostMapping("/add")
    public ApiResponse<String> addStudentForGroup(@AuthenticationPrincipal User user, @RequestParam("idStudent") Integer idStudent, @RequestParam("groupName") String groupName){
        return teacherService.addStudentForGroup(user, idStudent, groupName);
    }
}
