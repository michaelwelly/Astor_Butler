package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.table.TableReservationOrder;
import museon_online.astor_butler.table.TableReservationService;
import museon_online.astor_butler.table.TableReservationStatus;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TableReservationHandler {

    private final TelegramBot telegramBot;
    private final TableReservationService tableReservationService;

    public void handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        telegramBot.sendTextMessage(chatId, "Пожалуйста, выберите стол (в будущем — через интерактивную карту)");

        TableReservationOrder order = new TableReservationOrder();
        order.setStatus(TableReservationStatus.PENDING);

        tableReservationService.createReservation(order);

        telegramBot.sendTextMessage(chatId, "✅ Ваш стол зарезервирован. Ожидайте подтверждения.");
    }
}