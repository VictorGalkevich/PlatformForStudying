package by.itstep.application.entity;

import by.itstep.application.entity.type.StatusType;
import lombok.*;

import javax.persistence.*;

@ToString
@Entity
@Table(name = "assignments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Test test;
    @Enumerated(value = EnumType.STRING)
    private StatusType status;
    private Integer rating;

}
