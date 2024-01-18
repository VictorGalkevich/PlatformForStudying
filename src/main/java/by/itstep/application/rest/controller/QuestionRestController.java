package by.itstep.application.rest.controller;

import by.itstep.application.entity.Question;
import by.itstep.application.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionRestController {
    private final QuestionService questionService;

    @GetMapping("/{testId}")
    public Set<Question> getAllQuestionsFromTest(@PathVariable("testId") Long testId) {
        return questionService.getAllQuestionsFromTest(testId);
    }
}
