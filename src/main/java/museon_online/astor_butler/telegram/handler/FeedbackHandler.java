package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.feedback.FeedbackService;
import museon_online.astor_butler.feedback.FeedbackState;
import museon_online.astor_butler.feedback.FeedbackUserState;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private final FeedbackUserState userState;
    private final FeedbackService feedbackService;
    private final UserService userService;

    public boolean handle(Update update) {
        Message message = update.getMessage();
        Long telegramId = message.getFrom().getId();

        if (!userState.is(telegramId, FeedbackState.AWAITING_FEEDBACK)) {
            return false;
        }

        User user = userService.findUserByTelegramId(telegramId.toString());
        if (user == null) {
            log.warn("Пользователь не найден: {}", telegramId);
            return false;
        }

        feedbackService.saveFeedback(user, message.getText());
        userState.clear(telegramId);

        return true;
    }
}
