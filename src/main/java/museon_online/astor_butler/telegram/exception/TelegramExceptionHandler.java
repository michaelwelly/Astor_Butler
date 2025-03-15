package museon_online.astor_butler.telegram.exception;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;

@Component
public class TelegramExceptionHandler {

    public void handleException(TelegramApiException e) {
        // Тут можно настроить логику логирования, уведомлений и т.д.
        System.err.println("Ошибка Telegram API: " + e.getMessage());
        // Можно также пробросить исключение выше или залогировать его в лог-файл
    }
}
