package museon_online.astor_butler.telegram.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@Data
public class TelegramExceptionHandler {

    private TelegramBot bot;

    private static final String ERROR_MESSAGE = "🚀 Кажется, что-то пошло не так... Попробуйте ещё раз или просто расслабьтесь — мы уже работаем над этим 😉";

    public void handleException(TelegramApiException e, TelegramBot bot, Long chatId) {
        log.error("Ошибка Telegram API: {}", e.getMessage(), e);

        if (chatId != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(ERROR_MESSAGE);

            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException sendException) {
                log.error("Ошибка при отправке сообщения об ошибке: {}", sendException.getMessage(), sendException);
            }
        }
    }
}
