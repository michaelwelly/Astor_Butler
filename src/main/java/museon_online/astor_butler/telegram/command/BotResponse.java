package museon_online.astor_butler.telegram.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class BotResponse {

    private final String text;
    private final InlineKeyboardMarkup markup;
    // Можно добавить: imageUrl, parseMode, attachments, chatId и т.д.

    public BotResponse(String text) {
        this(text, null);
    }

    public BotResponse(String text, InlineKeyboardMarkup markup) {
        this.text = text;
        this.markup = markup;
    }

    public String getText() {
        return text;
    }

    public InlineKeyboardMarkup getMarkup() {
        return markup;
    }

    public boolean hasMarkup() {
        return markup != null;
    }
}
