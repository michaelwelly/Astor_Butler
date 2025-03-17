package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class OrderButton extends Button {

    public InlineKeyboardMarkup createOrderButton() {
        return createButton("🛒 Заказ", "/order");
    }
}
