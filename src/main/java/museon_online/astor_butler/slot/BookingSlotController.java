package museon_online.astor_butler.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/slot")
@RequiredArgsConstructor
public class BookingSlotController {

    private final BookingSlotService bookingSlotService;

    @GetMapping
    public List<BookingSlot> getAllSlots() {
        return bookingSlotService.getAllSlots();
    }

    @PostMapping
    public BookingSlot createSlot(@RequestBody BookingSlot bookingSlot) {
        return bookingSlotService.createSlot(bookingSlot);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable UUID id) {
        bookingSlotService.deleteSlot(id);
    }
}
