package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class RazjebButton extends Button {

    public InlineKeyboardMarkup createRazjebButton() {
        return createButton("🔥 Разъёб", "/razjeb");
    }
}
