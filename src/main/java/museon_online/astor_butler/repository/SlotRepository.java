package museon_online.astor_butler.repository;

import museon_online.astor_butler.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
    List<Slot> findByStartTime(LocalDateTime startTime);
}
