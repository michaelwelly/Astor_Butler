package museon_online.astor_butler.telegram.exception;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TelegramExceptionHandler {

    public void handleException(TelegramApiException e) {
        log.error("Ошибка Telegram API: {}", e.getMessage(), e);
    }
}

