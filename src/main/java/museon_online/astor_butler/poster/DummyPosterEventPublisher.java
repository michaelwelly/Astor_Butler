package museon_online.astor_butler.poster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DummyPosterEventPublisher implements PosterEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(DummyPosterEventPublisher.class);

    @Override
    public void publish(EventPoster poster) {
        log.info("[DUMMY] Published poster event: {}", poster.getTitle());
    }
}