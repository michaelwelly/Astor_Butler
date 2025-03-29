package museon_online.astor_butler.slot;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlotSchedulerInitializer {

    private final BookingSlotService bookingSlotService;

    @PostConstruct
    public void init() {
        bookingSlotService.generateQuarterForAllLocations();
    }
}