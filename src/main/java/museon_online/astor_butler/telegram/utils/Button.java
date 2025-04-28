package museon_online.astor_butler.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Button {

    InlineKeyboardMarkup buildButton();

    String getCommand();

    String getDescription();
}