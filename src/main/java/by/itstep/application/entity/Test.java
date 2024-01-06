package by.itstep.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean access = true; // for custom close access
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private String createBy;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tests_questions", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;

    public boolean isTestAccessible() {
        LocalDateTime now = LocalDateTime.now();
        return access && (startTime == null || now.isAfter(startTime))
                      && (endTime == null || now.isBefore(endTime));
    }
}
