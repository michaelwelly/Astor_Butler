package museon_online.astor_butler.slot;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.table.RestaurantTable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantTable table;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingSlotStatus status;

    @Enumerated(EnumType.STRING)
    private SlotType type;

    private boolean isAutoGenerated;
}
