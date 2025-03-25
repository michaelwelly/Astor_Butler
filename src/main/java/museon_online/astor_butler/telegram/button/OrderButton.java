package museon_online.astor_butler.telegram.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderButton {

    public InlineKeyboardMarkup createOrderButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(List.of(
                createInlineButton("🍽 Забронировать стол", "order_table"),
                createInlineButton("🛍 Заказать мерч", "order_merch"),
                createInlineButton("❤️ Пожертвование", "order_charity")
        ));

        keyboard.add(List.of(
                createInlineButton("🔙 Назад", "back"),
                createInlineButton("🏠 Главное меню", "main_menu")
        ));

        markup.setKeyboard(keyboard);
        return markup;
    }

    private InlineKeyboardButton createInlineButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }
}
