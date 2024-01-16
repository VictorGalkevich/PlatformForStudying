package by.itstep.application.service.question;

import by.itstep.application.entity.Question;
import by.itstep.application.util.GetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final GetEntity getEntity;

    @Transactional(readOnly = true)
    public Set<Question> getAllQuestionsFromTest(Integer testId) {
        var test = getEntity.getTestById(testId);
        return test.getQuestions();
    }

//    @Transactional(readOnly = true)
//    public ResponseEntity<?> getAllQuestionsFromTest(Integer testId) {
//        var test = getEntity.getTestById(testId);
//
//        if (test == null) {
//            return new ResponseEntity<>("Test not found", HttpStatus.NOT_FOUND);
//        }
//
//        if (!test.isTestAccessible()) {
//            return new ResponseEntity<>("Test is not currently accessible", HttpStatus.FORBIDDEN);
//        }
//
//        if (test.getStartTime() == null || test.getEndTime() == null) {
//            return new ResponseEntity<>("Please add timings for the test", HttpStatus.BAD_REQUEST);
//        }
//
//        LocalDateTime now = LocalDateTime.now();
//
//        if (now.isBefore(test.getStartTime())) {
//            return new ResponseEntity<>("Test is not yet available", HttpStatus.FORBIDDEN);
//        }
//
//        if (now.isAfter(test.getEndTime())) {
//            return new ResponseEntity<>("Test is no longer available", HttpStatus.FORBIDDEN);
//        }
//
//        if (test.getDuration() != null) {
//            Duration testDuration = Duration.ofMinutes(test.getDuration());
//            LocalDateTime endTimeForStudent = now.plus(testDuration);
//
//            if (endTimeForStudent.isAfter(test.getEndTime())) {
//                test.setEndTime(endTimeForStudent);
//            }
//        }
//
//        return new ResponseEntity<>(test.getQuestions(), HttpStatus.OK);
//    }
}
