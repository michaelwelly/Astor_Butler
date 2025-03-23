package museon_online.astor_butler.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "table_reservation_order")
public class TableReservationOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "number_of_people", nullable = false)
    private int numberOfPeople;

    @Column(name = "table_number", nullable = false)
    private String tableNumber;

    public LocalDateTime getStartTime() {
        return reservationTime;
    }

    public LocalDateTime getEndTime() {
        return reservationTime.plusHours(2); // Резерв на 2 часа
    }

}
