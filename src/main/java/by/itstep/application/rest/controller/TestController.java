package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.entity.User;
import by.itstep.application.service.test.TestService;
import by.itstep.application.service.test.TestTimeRequest;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @PostMapping("/create")
    public ApiResponse<String> createTest(@AuthenticationPrincipal User user, @RequestBody List<Question> questions) {
        return testService.createTest(user, questions);
    }

    @PostMapping ("/time")
    public ApiResponse<String> setTimingsForTest(@RequestBody TestTimeRequest testRequest) {
        return testService.setTimingsForTest(testRequest);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTest(@PathVariable("id") Integer id){
        return testService.getTest(id);
    }

    @PostMapping("/pass")
    public ApiResponse<String> passTest(@AuthenticationPrincipal User user){
        return testService.passTest(user);
    }

//

}
