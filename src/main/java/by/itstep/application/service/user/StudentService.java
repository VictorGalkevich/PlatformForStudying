package by.itstep.application.service.user;

import by.itstep.application.entity.Student;
import by.itstep.application.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    public void registerStudent(Student newStudent) {
        studentRepository.save(newStudent);
    }
}
