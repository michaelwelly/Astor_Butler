package museon_online.astor_butler.poster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "event_posters")
public class EventPoster {

    @Id
    private UUID id;

    private LocalDate date;

    private String title;

    @Column(name = "raw_text")
    private String rawText;

    private String source;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static EventPoster fromDto(EventPosterDto dto) {
        return new EventPoster(
                UUID.randomUUID(),
                dto.getDate(),
                dto.getTitle(),
                dto.getRawText(),
                dto.getSource(),
                LocalDateTime.now()
        );
    }
}