package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.entity.Test;
import by.itstep.application.service.test.TestService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final TestService testService;

    @PostMapping("/create")
    public ApiResponse<String> createTest(@RequestBody List<Question> questions) {
        return testService.createTest(questions);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Test> getTest(@PathVariable("id") Integer id){
        return testService.getTest(id);
    }
}
