package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class TableButton extends Button {

    public InlineKeyboardMarkup createTableButton() {
        return createButton("📅 Забронировать стол", "/book_table");
    }
}
