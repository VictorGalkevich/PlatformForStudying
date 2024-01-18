package by.itstep.application.service.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestTimeRequest {
    private Long idTest;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
}
