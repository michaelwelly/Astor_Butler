package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.TableReservationOrder;
import museon_online.astor_butler.model.ReservationStatus;
import museon_online.astor_butler.repository.TableReservationOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TableReservationService {

    private final TableReservationOrderRepository reservationOrderRepository;

    @Transactional
    public boolean isTableAvailable(String tableNumber, LocalDateTime startTime, LocalDateTime endTime) {
        return !reservationOrderRepository.existsByTableNumberAndReservationDateAndReservationTimeBetween(
                tableNumber, startTime, endTime);
    }

    @Transactional
    public UUID createReservation(TableReservationOrder order) {
        if (!isTableAvailable(order.getTableNumber(), order.getStartTime(), order.getEndTime())) {
            throw new IllegalStateException("Table is not available");
        }
        order.setStatus(ReservationStatus.PENDING);
        TableReservationOrder savedOrder = reservationOrderRepository.save(order);

        return savedOrder.getId();
    }

    @Transactional(readOnly = true)
    public List<TableReservationOrder> getAllReservations() {
        return reservationOrderRepository.findAll();
    }

    @Transactional
    public TableReservationOrder confirmReservation(UUID id) {
        TableReservationOrder order = reservationOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
        order.setStatus(ReservationStatus.CONFIRMED);
        return reservationOrderRepository.save(order);
    }

    @Transactional
    public TableReservationOrder cancelReservation(UUID id) {
        TableReservationOrder order = reservationOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with id: " + id));
        order.setStatus(ReservationStatus.CANCELED);
        return reservationOrderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<TableReservationOrder> getReservationsByStatus(ReservationStatus status) {
        return reservationOrderRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<TableReservationOrder> getReservationsByUserAndStatus(UUID userId, ReservationStatus status) {
        return reservationOrderRepository.findByUserIdAndStatus(userId, status);
    }

}
