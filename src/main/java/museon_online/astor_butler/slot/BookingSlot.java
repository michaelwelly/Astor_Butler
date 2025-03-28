package museon_online.astor_butler.slot;

import jakarta.persistence.*;
import lombok.*;

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
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RestaurantTable table;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingSlotStatus status;

    @Enumerated(EnumType.STRING)
    private SlotType type; // NEW — тип: стол, бар, танцпол

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }
}
