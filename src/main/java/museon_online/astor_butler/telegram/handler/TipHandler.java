package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.finance.balance.BalanceService;
import museon_online.astor_butler.finance.balance.WalletType;
import museon_online.astor_butler.telegram.utils.AstorUpdate;
import museon_online.astor_butler.tip.TipState;
import museon_online.astor_butler.tip.TipUserContext;
import museon_online.astor_butler.tip.TipUserStateService;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class TipHandler implements AstorBotHandler {

    private final TipUserStateService tipStateService;
    private final BalanceService balanceService;
    private final UserService userService;

    @Override
    public boolean appliesTo(AstorUpdate update) {
        return update.text() != null && (update.text().startsWith("/tip")
                || tipStateService.getState(update.raw().getMessage().getFrom().getId()) != null);
    }

    @Override
    public void handle(AstorUpdate update) {
        Long telegramId = update.raw().getMessage().getFrom().getId();
        String text = update.text();

        TipState state = tipStateService.getState(telegramId);

        if (state == null) {
            update.reply("💸 Введите сумму чаевых (в рублях):");
            tipStateService.setState(telegramId, TipState.AWAITING_AMOUNT);
            return;
        }

        switch (state) {
            case AWAITING_AMOUNT -> {
                try {
                    BigDecimal amount = new BigDecimal(text);
                    tipStateService.setContext(telegramId, new TipUserContext(amount));
                    tipStateService.setState(telegramId, TipState.CONFIRMATION);
                    update.reply("Подтвердите списание " + amount + " ₽. Напишите 'да' или 'нет'.");
                } catch (Exception e) {
                    update.reply("❗️ Введите корректную сумму чаевых");
                }
            }
            case CONFIRMATION -> {
                if (text.equalsIgnoreCase("да")) {
                    TipUserContext context = tipStateService.getContext(telegramId);
                    User user = userService.findUserByTelegramId(telegramId.toString());
                    balanceService.charge(user.getId(), context.getAmount(), WalletType.RUB, "Чаевые через бот");
                    update.reply("🙏 Спасибо! Чаевые отправлены.");
                    tipStateService.clear(telegramId);
                } else {
                    update.reply("❌ Чаевые отменены.");
                    tipStateService.clear(telegramId);
                }
            }
        }
    }
}
