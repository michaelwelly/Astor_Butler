package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class FeedbackButton {
    public InlineKeyboardMarkup createFeedbackButton() {
        InlineKeyboardButton button = new InlineKeyboardButton("📝 Оставить отзыв");
        button.setCallbackData("feedback_start");
        return new InlineKeyboardMarkup(List.of(List.of(button)));
    }
}
