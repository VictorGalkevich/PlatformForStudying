package by.itstep.application.rest.request;

import by.itstep.application.entity.type.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionRequest {
    private Integer id;
    private int idTest;
    private String text;
    private String userAnswer;
    private String rightAnswer;
    private List<String> possibleAnswers;
    private QuestionType type;

}
