package museon_online.astor_butler.table;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class TableReservationController {

    private final TableReservationService reservationService;

    // ✅ Создание новой брони
    @PostMapping
    public ResponseEntity<UUID> createReservation(@RequestBody TableReservationOrder order) {
        UUID reservationId = reservationService.createReservation(order);
        return ResponseEntity.ok(reservationId);
    }

    // ✅ Получение всех бронирований
    @GetMapping
    public ResponseEntity<List<TableReservationOrder>> getAllReservations() {
        List<TableReservationOrder> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // ✅ Подтверждение брони
    @PostMapping("/{id}/confirm")
    public ResponseEntity<TableReservationOrder> confirmReservation(@PathVariable UUID id) {
        TableReservationOrder order = reservationService.confirmReservation(id);
        return ResponseEntity.ok(order);
    }

    // ✅ Отмена брони
    @PostMapping("/{id}/cancel")
    public ResponseEntity<TableReservationOrder> cancelReservation(@PathVariable UUID id) {
        TableReservationOrder order = reservationService.cancelReservation(id);
        return ResponseEntity.ok(order);
    }

    // ✅ Получение бронирований по статусу
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TableReservationOrder>> getReservationsByStatus(@PathVariable TableReservationStatus status) {
        List<TableReservationOrder> reservations = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    // ✅ Получение бронирований пользователя по статусу
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<TableReservationOrder>> getReservationsByUserAndStatus(
            @PathVariable UUID userId,
            @PathVariable TableReservationStatus status) {
        List<TableReservationOrder> reservations = reservationService.getReservationsByUserAndStatus(userId, status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkTableAvailability(
            @RequestParam String tableNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {

        boolean available = reservationService.isTableAvailable(tableNumber, startTime, endTime);
        return ResponseEntity.ok(available);
    }
}
