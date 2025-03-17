package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class BalanceButton extends Button {

    public InlineKeyboardMarkup createBalanceButton() {
        return createButton("💰 Баланс", "/balance");
    }
}
