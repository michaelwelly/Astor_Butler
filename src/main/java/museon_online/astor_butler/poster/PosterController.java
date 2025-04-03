package museon_online.astor_butler.poster;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/posters")
public class PosterController {

    private final EventPosterRepository repository;

    public PosterController(EventPosterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/today")
    public List<EventPoster> today() {
        LocalDate now = LocalDate.now();
        return repository.findByDateBetween(now, now);
    }

    @GetMapping("/week")
    public List<EventPoster> week() {
        LocalDate now = LocalDate.now();
        return repository.findByDateBetween(now, now.plusDays(7));
    }

    @GetMapping("/all")
    public List<EventPoster> all() {
        return repository.findAllByOrderByDateAsc();
    }
}