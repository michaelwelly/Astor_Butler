package museon_online.astor_butler.slot;

import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, Long> {

    boolean existsByLocationAndTableAndStartTime(Location location, RestaurantTable table, LocalDateTime startTime);

    List<BookingSlot> findAllByLocationAndStatusAndStartTimeBetween(
            Location location,
            BookingSlotStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    Optional<BookingSlot> findFirstByLocationAndTypeAndStatusAndStartTimeAfterOrderByStartTimeAsc(
            Location location,
            SlotType type,
            BookingSlotStatus status,
            LocalDateTime after
    );
}