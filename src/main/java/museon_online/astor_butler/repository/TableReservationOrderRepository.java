package museon_online.astor_butler.repository;

import museon_online.astor_butler.model.TableReservationOrder;
import museon_online.astor_butler.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TableReservationOrderRepository extends JpaRepository<TableReservationOrder, UUID> {

    List<TableReservationOrder> findByStatus(ReservationStatus status);

    List<TableReservationOrder> findByUserIdAndStatus(UUID userId, ReservationStatus status);


    boolean existsByTableNumberAndReservationDateAndReservationTimeBetween(
            String tableNumber,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    Optional<TableReservationOrder> findById(UUID id);
}

