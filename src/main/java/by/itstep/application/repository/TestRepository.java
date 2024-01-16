package by.itstep.application.repository;

import by.itstep.application.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query("SELECT DISTINCT t FROM Test t JOIN FETCH t.questions WHERE t.id = :id")
    Optional<Test> findByIdWithQuestions(@Param("id") Integer id);

    @Query("SELECT DISTINCT t FROM Test t JOIN FETCH t.questions")
    List<Test> findAllWithQuestions();
}
