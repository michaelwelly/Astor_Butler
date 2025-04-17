package museon_online.astor_butler.fsm;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserSession {
    private Long userId;
    private BookingState state = BookingState.SELECTING_LOCATION;
    private UUID locationId;
    private LocalDate date;
    private UUID slotId;
    private UUID tableId;
}
