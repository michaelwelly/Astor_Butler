package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.merch.MerchOrder;
import museon_online.astor_butler.merch.MerchOrderService;
import museon_online.astor_butler.merch.MerchOrderStatus;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class MerchOrderHandler {

    private final TelegramBot telegramBot;
    private final MerchOrderService merchOrderService;

    public void handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        telegramBot.sendTextMessage(chatId, "Выберите мерч и укажите количество (в будущем через кнопки)");

        MerchOrder order = new MerchOrder();
        order.setProductName("Футболка Astor");
        order.setQuantity(1);
        order.setPrice(new BigDecimal("1990.00"));
        order.setStatus(MerchOrderStatus.PENDING);

        merchOrderService.createMerchOrder(order);

        telegramBot.sendTextMessage(chatId, "✅ Заказ мерча оформлен. Мы свяжемся с вами!");
    }
}