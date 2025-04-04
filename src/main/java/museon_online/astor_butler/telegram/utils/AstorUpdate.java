package museon_online.astor_butler.telegram.utils;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class AstorUpdate {

    private final Update update;
    private final TelegramBot bot;

    public String text() {
        Message msg = update.getMessage();
        return msg != null ? msg.getText() : null;
    }

    public Long chatId() {
        Message msg = update.getMessage();
        return msg != null ? msg.getChatId() : null;
    }

    public Update raw() {
        return update;
    }

    public void reply(String message) {
        bot.sendMessage(chatId(), message);
    }

    public static AstorUpdate wrap(Update update, TelegramBot bot) {
        return new AstorUpdate(update, bot);
    }
}
