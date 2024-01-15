package by.itstep.application.repository;

import by.itstep.application.entity.User;
import by.itstep.application.mapper.Mapper;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends
        JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User>
{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
           "SET a.enabled = TRUE WHERE a.email = ?1")
    void enableAppUser(String email);

    boolean existsByEmail(String email);
}
