package museon_online.astor_butler.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking-slots")
@RequiredArgsConstructor
public class BookingSlotController {

    private final BookingSlotService service;

    @PostMapping
    public ResponseEntity<BookingSlot> createSlot(@RequestBody BookingSlot slot) {
        return ResponseEntity.ok(service.create(slot));
    }

    @GetMapping
    public ResponseEntity<List<BookingSlot>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/status")
    public ResponseEntity<List<BookingSlot>> getByStatus(@RequestParam BookingSlotStatus status) {
        return ResponseEntity.ok(service.findByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BookingSlot> updateStatus(@PathVariable UUID id,
                                                    @RequestParam BookingSlotStatus status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookingSlot>> getAvailableSlots(
            @RequestParam UUID locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.findAvailableSlots(locationId, date));
    }

    @GetMapping("/nearest")
    public ResponseEntity<BookingSlot> getNearestSlot(
            @RequestParam UUID locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
            @RequestParam SlotType type
    ) {
        Location location = service.getLocationRepository().findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));

        return service.findNearestAvailableSlot(location, after, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
