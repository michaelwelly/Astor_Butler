package museon_online.astor_butler.slot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.table.TableReservationOrder;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking_slots")
@Data
@NoArgsConstructor
@Slf4j
public class BookingSlot {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private TableReservationOrder order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingSlotStatus status;

    @PrePersist
    public void logCreation() {
        log.info("Создан слот бронирования: {} — {} [{}]", startTime, endTime, status);
    }
}
