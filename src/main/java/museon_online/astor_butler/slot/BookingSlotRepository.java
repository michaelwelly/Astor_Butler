package museon_online.astor_butler.slot;

import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.table.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, UUID> {

    boolean existsByLocationAndTableAndStartTime(Location location, RestaurantTable table, LocalDateTime startTime);

    List<BookingSlot> findAllByLocation(Location location);

    Optional<BookingSlot> findByLocationAndTableAndStartTime(Location location, RestaurantTable table, LocalDateTime startTime);

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

    List<BookingSlot> findAllByLocationAndStatus(Location location, BookingSlotStatus status);
}