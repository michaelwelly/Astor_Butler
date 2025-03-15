package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.Slot;
import museon_online.astor_butler.repository.SlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;

    public Slot createSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    public Slot getSlotById(UUID id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
    }

    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    public List<Slot> getSlotsByDate(LocalDateTime startTime) {
        return slotRepository.findByStartTime(startTime);
    }

    public Slot updateSlot(UUID id, Slot updatedSlot) {
        Slot slot = getSlotById(id);
        slot.setStartTime(updatedSlot.getStartTime());
        slot.setEndTime(updatedSlot.getEndTime());
        slot.setAvailable(updatedSlot.isAvailable());
        slot.setOrder(updatedSlot.getOrder());
        return slotRepository.save(slot);
    }

    public void deleteSlot(UUID id) {
        slotRepository.deleteById(id);
    }
}
