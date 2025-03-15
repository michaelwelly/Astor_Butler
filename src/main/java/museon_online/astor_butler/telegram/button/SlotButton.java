package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class SlotButton extends Button {

    public InlineKeyboardMarkup createSlotButton() {
        return createButton("🕒 Выбрать слот", "/slots");
    }
}
