package museon_online.astor_butler.poster;

import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
