package by.itstep.application.entity;

import by.itstep.application.entity.type.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private String rightAnswer;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "possible_answers", joinColumns = @JoinColumn(name = "option_question_id"))
    private List<String> possibleAnswers;
    @Enumerated(EnumType.STRING)
    private QuestionType type;

}
