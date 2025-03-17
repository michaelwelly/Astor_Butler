package museon_online.astor_butler.telegram.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Component
public class TelegramExceptionHandler {

    private static final String ERROR_MESSAGE = "🚀 Кажется, что-то пошло не так... Попробуйте ещё раз или просто расслабьтесь — мы уже работаем над этим 😉";

    public void handleException(TelegramApiException e, AbsSender sender, Long chatId) {
        log.error("Ошибка Telegram API: {}", e.getMessage(), e);

        if (chatId != null) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setText(ERROR_MESSAGE);

            try {
                sender.execute(sendMessage);
            } catch (TelegramApiException sendException) {
                log.error("Ошибка при отправке сообщения об ошибке: {}", sendException.getMessage(), sendException);
            }
        }
    }
}
