package museon_online.astor_butler.poster;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PosterService {

    private final EventPosterRepository repository;
    private final PosterEventPublisher publisher;

    public PosterService(EventPosterRepository repository, PosterEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public UUID save(EventPosterDto dto) {
        EventPoster poster = EventPoster.fromDto(dto);
        repository.save(poster);
        publisher.publish(poster);
        return poster.getId();
    }

    public String getThisWeekPostersFormatted() {
        LocalDate now = LocalDate.now();
        LocalDate endOfWeek = now.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM");

        List<EventPoster> posters = repository.findAll().stream()
                .filter(p -> !p.getDate().isBefore(now) && !p.getDate().isAfter(endOfWeek))
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .collect(Collectors.toList());

        if (posters.isEmpty()) {
            return "На этой неделе афиш пока нет.";
        }

        return posters.stream()
                .map(p -> "📅 " + p.getDate().format(formatter) + " | " + p.getTitle())
                .collect(Collectors.joining("\n"));
    }
}
