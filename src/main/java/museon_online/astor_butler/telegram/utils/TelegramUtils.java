package museon_online.astor_butler.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUtils {

    public static Long getChatIdFromUpdate(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return null;
    }

    public static String extractHash(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (messageText.startsWith("/start") && messageText.contains(" ")) {
                // пример: /start 1234567890abcdef
                return messageText.split(" ")[1].trim();
            }
        }
        return "";
    }
}