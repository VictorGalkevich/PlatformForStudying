package by.itstep.application.rest.controller;

import by.itstep.application.entity.User;
import by.itstep.application.service.user.TeacherService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/assign")
    public ApiResponse<String> assignTestForStudent(@AuthenticationPrincipal User user,
            @RequestParam("idTest") Integer idTest,
            @RequestParam("groupName") String groupName){
        return teacherService.assignTestForStudent(user, idTest, groupName);
    }

    @PostMapping("/send")
    public ApiResponse<String> sendResultTest(@AuthenticationPrincipal User user,
                                              @RequestParam("rating")Integer rating,
                                              @RequestParam("idAssigment") Integer idAssigment) {
        return teacherService.sendResultTest(user, rating, idAssigment);
    }
}
