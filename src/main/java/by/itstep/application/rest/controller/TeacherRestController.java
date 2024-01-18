package by.itstep.application.rest.controller;

import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import by.itstep.application.service.user.TeacherService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherRestController {
    private final TeacherService teacherService;

    @PostMapping("/assign")
    public ApiResponse<String> assignTestForGroup(@AuthenticationPrincipal User user,
                                                  @RequestParam("idTest") Long idTest,
                                                  @RequestParam("idGroup") Long idGroup){
       return teacherService.assignTestForGroup(user, idTest, idGroup);
    }

    @PostMapping("/send")
    public ApiResponse<String> sendResultTest(@AuthenticationPrincipal User user,
                                              @RequestParam("rating")Integer rating,
                                              @RequestParam("idAssigment") Long idAssigment) {
        return teacherService.sendResultTest(user, rating, idAssigment);
    }

    @GetMapping("/tests")
    public List<Test> getAllTests(){
        return teacherService.getAllTests();
    }
}
