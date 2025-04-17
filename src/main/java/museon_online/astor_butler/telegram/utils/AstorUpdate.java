package museon_online.astor_butler.telegram.utils;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class AstorUpdate {

    private final Update update;

    public String text() {
        Message msg = update.getMessage();
        return msg != null ? msg.getText() : null;
    }

    public Long chatId() {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else if (update.getCallbackQuery() != null && update.getCallbackQuery().getMessage() != null) {
            return update.getCallbackQuery().getMessage().getChatId();
        } else if (update.getEditedMessage() != null) {
            return update.getEditedMessage().getChatId();
        }
        return null;
    }

    public Update raw() {
        return update;
    }
}
