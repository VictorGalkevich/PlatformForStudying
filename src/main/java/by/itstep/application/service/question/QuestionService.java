package by.itstep.application.service.question;

import by.itstep.application.entity.Question;
import by.itstep.application.repository.TestRepository;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final GetEntity getEntity;
    public List<Question> getAllQuestionsFromTest(Integer testId) {
        var test = getEntity.getTestById(testId);
        return test.getQuestions();
    }
}
