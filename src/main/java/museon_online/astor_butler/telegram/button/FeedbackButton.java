package museon_online.astor_butler.telegram.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class FeedbackButton extends Button {

    public InlineKeyboardMarkup createFeedbackButton() {
        return createButton("📝 Оставить отзыв", "/feedback");
    }
}
