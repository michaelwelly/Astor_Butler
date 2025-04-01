package museon_online.astor_butler.slot;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.location.LocationService;
import museon_online.astor_butler.slot.dto.ManualSlotRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking-slots")
@RequiredArgsConstructor
public class BookingSlotController {

    private final BookingSlotService service;
    private final LocationService locationService;

    @PostMapping("/manual")
    public ResponseEntity<BookingSlot> manualCreate(@RequestBody ManualSlotRequest request) {
        BookingSlot slot = service.createOrReserveSlot(
                request.locationId(),
                request.tableId(),
                request.startTime(),
                request.endTime()
        );
        return ResponseEntity.ok(slot);
    }

    @GetMapping("/status")
    public ResponseEntity<List<BookingSlot>> getByStatusAndLocation(
            @RequestParam UUID locationId,
            @RequestParam BookingSlotStatus status
    ) {
        return ResponseEntity.ok(service.findByStatusAndLocation(locationId, status));
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
        Location location = locationService.getById(locationId);
        return service.findNearestAvailableSlot(location, after, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
