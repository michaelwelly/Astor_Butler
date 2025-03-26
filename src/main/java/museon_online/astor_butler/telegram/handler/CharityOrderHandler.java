package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.charity.CharityOrder;
import museon_online.astor_butler.charity.CharityOrderService;
import museon_online.astor_butler.charity.CharityOrderStatus;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharityOrderHandler {

    private final TelegramBot telegramBot;
    private final CharityOrderService charityOrderService;

    public void handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        telegramBot.sendTextMessage(chatId, "Укажите сумму пожертвования (например, 100):");

        // В дальнейшем сумму лучше брать из состояния пользователя
        CharityOrder order = new CharityOrder();
        order.setDonationAmount(new BigDecimal("100.00"));
        order.setMessage("Благотворительность через Telegram");
        order.setStatus(CharityOrderStatus.PENDING);

        charityOrderService.createCharityOrder(order);

        telegramBot.sendTextMessage(chatId, "❤️ Спасибо за пожертвование! Оно будет использовано на благие цели.");
    }
}