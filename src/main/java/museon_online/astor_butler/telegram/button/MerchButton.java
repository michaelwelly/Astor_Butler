package museon_online.astor_butler.telegram.button;

import museon_online.astor_butler.telegram.utils.Button;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MerchButton implements Button {

    @Override
    public InlineKeyboardMarkup buildButton() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("🛍 Мерч");
        button.setCallbackData("/order");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(List.of(List.of(button)));
        return markup;
    }

    @Override
    public String getCommand() {
        return "/order";
    }

    @Override
    public String getDescription() {
        return "Заказать мерч и сувениры";
    }
}