package museon_online.astor_butler.poster;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class EventPosterDto {
    private LocalDate date;
    private String title;
    private String rawText;
    private String source;
}

