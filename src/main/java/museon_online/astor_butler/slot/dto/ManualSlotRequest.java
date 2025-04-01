package museon_online.astor_butler.slot.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ManualSlotRequest(
        UUID locationId,
        UUID tableId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}