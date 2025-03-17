package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class AfishaButton extends Button {

    public InlineKeyboardMarkup createAfishaButton() {
        return createButton("🎭 Афиша", "/afisha");
    }
}
