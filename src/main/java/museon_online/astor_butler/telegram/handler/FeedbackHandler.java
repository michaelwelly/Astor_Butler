package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.feedback.FeedbackService;
import museon_online.astor_butler.feedback.FeedbackState;
import museon_online.astor_butler.feedback.FeedbackUserState;
import museon_online.astor_butler.finance.balance.BalanceService;
import museon_online.astor_butler.finance.balance.WalletType;
import museon_online.astor_butler.telegram.utils.AstorUpdate;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackHandler {

    private final FeedbackUserState userState;
    private final FeedbackService feedbackService;
    private final UserService userService;
    private final BalanceService balanceService;

    @Override
    public boolean appliesTo(AstorUpdate update) {
        return update.text() != null && update.text().startsWith("/feedback");
    }

    @Override
    public void handle(AstorUpdate update) {
        Message message = update.raw().getMessage();
        Long telegramId = message.getFrom().getId();

        if (!userState.is(telegramId, FeedbackState.AWAITING_FEEDBACK)) {
            update.reply("📝 Для отправки отзыва введите его после команды /feedback");
            return;
        }

        User user = userService.findUserByTelegramId(telegramId.toString());
        if (user == null) {
            log.warn("Пользователь не найден: {}", telegramId);
            update.reply("⚠️ Ошибка: пользователь не найден");
            return;
        }

        feedbackService.saveFeedback(user, message.getText());
        userState.clear(telegramId);

        if (!feedbackService.hasReceivedReward(user.getId())) {
            balanceService.add(user.getId(), new BigDecimal("5"), WalletType.STARS, "Отзыв о мероприятии");
            feedbackService.markRewardGiven(user.getId());
            update.reply("✨ Спасибо за отзыв! Вам начислено 5 Telegram Stars.");
        } else {
            update.reply("💬 Спасибо за отзыв! Stars уже начислены ранее ✨");
        }
    }
}