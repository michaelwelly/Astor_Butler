package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class MenuButton extends Button {

    public InlineKeyboardMarkup createMenuButton() {
        return createButton("🍽️ Открыть меню", "/menu");
    }
}
