package museon_online.astor_butler.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramUtils {

    private TelegramUtils() {
    }

    public static Long getChatIdFromUpdate(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return null;
    }
}
