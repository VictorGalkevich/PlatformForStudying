package by.itstep.application.service.test;

import by.itstep.application.entity.Question;
import by.itstep.application.entity.Test;
import by.itstep.application.repository.QuestionRepository;
import by.itstep.application.repository.TestRepository;
import by.itstep.application.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    @Transactional
    public ApiResponse<String> createTest(final List<Question> questions) {
        var test = new Test();
        questionRepository.saveAll(questions);
        test.setQuestions(questions);
        testRepository.save(test);
        return ApiResponse.success("test save");
    }

//    @Transactional(readOnly = true)
//    public ResponseEntity<Test> getTest(Integer id) {
//      var test =  testRepository.findById(id).get();
//        return new ResponseEntity<>(test, HttpStatus.OK);
//    }

    @Transactional(readOnly = true)
    public ResponseEntity<Test> getTest(Integer id) {
        var test = testRepository.findByIdWithQuestions(id).orElse(null);
        return test != null
                ? new ResponseEntity<>(test, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
