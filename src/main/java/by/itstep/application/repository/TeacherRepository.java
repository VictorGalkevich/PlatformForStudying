package by.itstep.application.repository;

import by.itstep.application.entity.Group;
import by.itstep.application.entity.Teacher;
import by.itstep.application.entity.Test;
import by.itstep.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
    Optional<Teacher> findByUser(User user);
    @Query("SELECT DISTINCT g FROM Teacher t JOIN t.groups g WHERE t.id = :teacherId")
    List<Group> findGroupsByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT DISTINCT t.tests FROM Teacher t WHERE t.user.id = :userId")
    List<Test> findAllTestsByTeacherId(@Param("userId") Long userId);
}
