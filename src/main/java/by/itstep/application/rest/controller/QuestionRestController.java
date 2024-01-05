package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.rest.request.QuestionRequest;
import by.itstep.application.service.question.QuestionService;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/question")
public class QuestionRestController {
    private final QuestionService questionService;
    @GetMapping("/add")
    public ApiResponse<String> addQuestionForTest(@RequestBody final List<Question> questions){

       return null;
    }
}
