package museon_online.astor_butler.telegram.fsm;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.location.Location;
import museon_online.astor_butler.location.LocationRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
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

        switch (session.getState()) {
            case SELECTING_LOCATION:
                return handleLocationSelection(session, message);
            // TODO: SELECTING_DATE, SELECTING_SLOT и далее
            default:
                return "Что-то пошло не так 😕";
        }
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
}
