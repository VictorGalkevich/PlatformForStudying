package by.itstep.application.entity;

import by.itstep.application.entity.type.StatusType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Test test;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "assignments_answers", joinColumns = @JoinColumn(name = "answer_id"))
    private List<String> userAnswers;
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
    private Integer rating;

}
