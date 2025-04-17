package museon_online.astor_butler.fsm;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.location.LocationRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingHandler {

    private final UserSessionManager sessionManager;
    private final LocationRepository locationRepository;

    public String handle(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        UserSession session = sessionManager.getOrCreateSession(userId);
        Message message = update.getMessage();

        return switch (session.getState()) {
            case SELECTING_LOCATION -> handleLocationSelection(session, message);
            case SELECTING_DATE    -> handleDateSelection(session, message);
            case SELECTING_SLOT    -> handleSlotSelection(session, message);
            case CONFIRMING        -> handleConfirmation(session, message);
            default                -> "Что-то пошло не так 😕";
        };
    }

    private String handleLocationSelection(UserSession session, Message message) {
        String input = message.getText().trim();
        List<Location> locations = locationRepository.findAll();

        for (Location location : locations) {
            if (location.getName().equalsIgnoreCase(input)) {
                session.setLocationId(location.getId());
                session.setState(BookingState.SELECTING_DATE);
                return "📅 Отлично! Теперь выберите дату (в формате ГГГГ-ММ-ДД):";
            }
        }

        String allNames = locations.stream()
                .map(Location::getName)
                .collect(Collectors.joining("\n- "));
        return "🏠 Пожалуйста, выберите одно из доступных заведений:\n\n- " + allNames;
    }

    private String handleDateSelection(UserSession session, Message message) {
        String input = message.getText().trim();
        try {
            session.setDate(LocalDate.parse(input));
            session.setState(BookingState.SELECTING_SLOT);
            return "🕒 Отлично! Теперь выберите слот (например, 18:00):";
        } catch (DateTimeParseException e) {
            return "❌ Неверный формат даты. Пожалуйста, введите в формате ГГГГ-ММ-ДД.";
        }
    }

    private String handleSlotSelection(UserSession session, Message message) {
        String input = message.getText().trim();
        session.setSlotId(UUID.nameUUIDFromBytes(input.getBytes())); // можно заменить на actual slot ID позже
        session.setState(BookingState.CONFIRMING);
        return String.format("🧾 Подтверждаете бронь?\n📍 %s\n📅 %s\n🕒 %s\n\nВведите 'да' для подтверждения или 'нет' для отмены.",
                session.getLocationId(), session.getDate(), input);
    }

    private String handleConfirmation(UserSession session, Message message) {
        String input = message.getText().trim().toLowerCase();
        return switch (input) {
            case "да"  -> "✅ Ваша бронь подтверждена! Ждём вас!";
            case "нет" -> {
                sessionManager.clearSession(session.getUserId());
                yield "❌ Бронь отменена. Начнём сначала?";
            }
            default -> "❓ Пожалуйста, введите 'да' или 'нет'.";
        };
    }
}
