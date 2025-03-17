package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class TableHandler {

    public String handleTableBooking() {
        // Логика бронирования стола
        return "📅 Стол успешно забронирован!";
    }
}
