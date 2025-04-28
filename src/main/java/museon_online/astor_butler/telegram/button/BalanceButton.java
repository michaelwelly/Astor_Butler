package museon_online.astor_butler.telegram.button;

import museon_online.astor_butler.telegram.utils.Button;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalanceButton implements Button {

    @Override
    public InlineKeyboardMarkup buildButton() {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("⭐ Баланс");
        button.setCallbackData("/balance");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(List.of(List.of(button)));
        return markup;
    }

    @Override
    public String getCommand() {
        return "/balance";
    }

    @Override
    public String getDescription() {
        return "Посмотреть и пополнить баланс";
    }
}