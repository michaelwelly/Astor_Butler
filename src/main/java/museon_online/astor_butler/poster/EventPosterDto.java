package museon_online.astor_butler.poster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventPosterDto {
    private LocalDate date;
    private String title;
    private String rawText;
    private String source;
}

