package museon_online.astor_butler.telegram.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TableReservationButton {

    public InlineKeyboardMarkup createReservationButton() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
                .text("Забронировать стол")
                .callbackData("/book_table")
                .build());

        markup.setKeyboard(List.of(row));
        return markup;
    }
}