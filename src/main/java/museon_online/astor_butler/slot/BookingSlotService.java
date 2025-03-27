package museon_online.astor_butler.slot;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingSlotService {

    private final BookingSlotRepository bookingSlotRepository;

    public BookingSlot create(BookingSlot slot) {
        slot.setStatus(BookingSlotStatus.AVAILABLE);
        BookingSlot saved = bookingSlotRepository.save(slot);
        log.info("Создан слот: {}", saved);
        return saved;
    }

    public List<BookingSlot> findAll() {
        return bookingSlotRepository.findAll();
    }

    public List<BookingSlot> findByStatus(BookingSlotStatus status) {
        return bookingSlotRepository.findByStatus(status);
    }

    public BookingSlot updateStatus(UUID id, BookingSlotStatus newStatus) {
        BookingSlot slot = bookingSlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Слот не найден: " + id));
        slot.setStatus(newStatus);
        BookingSlot updated = bookingSlotRepository.save(slot);
        log.info("Статус слота обновлён: {} → {}", id, newStatus);
        return updated;
    }
}