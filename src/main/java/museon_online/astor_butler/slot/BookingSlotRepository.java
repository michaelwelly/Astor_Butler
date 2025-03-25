package museon_online.astor_butler.slot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, UUID> {
    List<BookingSlot> findByStartTime(LocalDateTime startTime);
}
