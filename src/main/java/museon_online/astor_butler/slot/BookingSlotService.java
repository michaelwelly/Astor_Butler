package museon_online.astor_butler.slot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingSlotService {

    private final BookingSlotRepository bookingSlotRepository;

    public BookingSlot createSlot(BookingSlot bookingSlot) {
        return bookingSlotRepository.save(bookingSlot);
    }

    public BookingSlot getSlotById(UUID id) {
        return bookingSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
    }

    public List<BookingSlot> getAllSlots() {
        return bookingSlotRepository.findAll();
    }

    public List<BookingSlot> getSlotsByDate(LocalDateTime startTime) {
        return bookingSlotRepository.findByStartTime(startTime);
    }

    public BookingSlot updateSlot(UUID id, BookingSlot updatedBookingSlot) {
        BookingSlot bookingSlot = getSlotById(id);
        bookingSlot.setStartTime(updatedBookingSlot.getStartTime());
        bookingSlot.setEndTime(updatedBookingSlot.getEndTime());
        bookingSlot.setAvailable(updatedBookingSlot.isAvailable());
        bookingSlot.setOrder(updatedBookingSlot.getOrder());
        return bookingSlotRepository.save(bookingSlot);
    }

    public void deleteSlot(UUID id) {
        bookingSlotRepository.deleteById(id);
    }
}
