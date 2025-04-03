package museon_online.astor_butler.poster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventPosterRepository extends JpaRepository<EventPoster, UUID> {

    List<EventPoster> findByDateBetween(LocalDate from, LocalDate to);

    List<EventPoster> findAllByOrderByDateAsc();
}