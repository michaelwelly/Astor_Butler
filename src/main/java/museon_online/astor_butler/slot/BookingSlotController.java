package museon_online.astor_butler.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}