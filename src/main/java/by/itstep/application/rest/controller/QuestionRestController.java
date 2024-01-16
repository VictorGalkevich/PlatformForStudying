package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.service.question.QuestionService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    private final QuestionService questionService;
    @GetMapping("/add")
    public ApiResponse<String> addQuestionForTest(@RequestBody final List<Question> questions){
       return null;
    }

    @GetMapping("/{testId}")
    public List<Question> getAllQuestionsFromTest(@PathVariable("testId") Integer testId) {
        return questionService.getAllQuestionsFromTest(testId);
    }
}
