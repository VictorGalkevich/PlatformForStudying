package by.itstep.application.rest.controller;

import by.itstep.application.entity.User;
import by.itstep.application.service.group.GroupService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupRestController {
    private final GroupService groupService;
    @PostMapping("/create")
    public ApiResponse<String> createGroup(@AuthenticationPrincipal User user, @RequestParam("groupName") String groupName) {
        return groupService.createGroup(user, groupName);
    }
    @PostMapping("/add")
    public ApiResponse<String> addStudentForGroup(@AuthenticationPrincipal User user, @RequestParam("idStudent") Integer idStudent, @RequestParam("groupName") String groupName) {
        return groupService.addStudentForGroup(user, idStudent, groupName);
    }

}
