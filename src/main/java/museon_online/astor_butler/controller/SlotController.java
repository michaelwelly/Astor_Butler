package museon_online.astor_butler.controller;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.Slot;
import museon_online.astor_butler.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/slot")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @GetMapping
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @PostMapping
    public Slot createSlot(@RequestBody Slot slot) {
        return slotService.createSlot(slot);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable UUID id) {
        slotService.deleteSlot(id);
    }
}
