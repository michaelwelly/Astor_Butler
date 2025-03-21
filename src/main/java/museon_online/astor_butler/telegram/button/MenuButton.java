package museon_online.astor_butler.telegram.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuButton {

    public InlineKeyboardMarkup createMenuButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(List.of(
                createInlineButton("📖 MENU AERIS", "menu_aeris"),
                createInlineButton("🍷 AERIS WINE ROOM", "wine_room")
        ));
        keyboard.add(List.of(
                createInlineButton("🥂 AERIS 10 MENU", "menu_10"),
                createInlineButton("🍽 AERIS DAILY MENU", "daily_menu")
        ));
        keyboard.add(List.of(
                createInlineButton("🍹 BAR AERIS", "bar_aeris"),
                createInlineButton("🔬 ELEMENTS CARD", "elements_card")
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
