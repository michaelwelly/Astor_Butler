package museon_online.astor_butler.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TableReservationOrderRepository extends JpaRepository<TableReservationOrder, UUID> {

    List<TableReservationOrder> findByStatus(TableReservationStatus status);

    List<TableReservationOrder> findByUserIdAndStatus(UUID userId, TableReservationStatus status);


    boolean existsByTableNumberAndReservationDateAndReservationTimeBetween(
            String tableNumber,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    Optional<TableReservationOrder> findById(UUID id);
}

