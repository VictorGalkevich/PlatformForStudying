package by.itstep.application.repository;

import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{

    Optional<Teacher> findByUser(User user);
}
