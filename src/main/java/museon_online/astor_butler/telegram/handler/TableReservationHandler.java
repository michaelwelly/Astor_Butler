package museon_online.astor_butler.telegram.handler;

import museon_online.astor_butler.table.TableReservationService;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TableReservationHandler {

    private final TelegramBot telegramBot;
    private final TableReservationService tableReservationService;

    @Autowired
    public TableReservationHandler(TelegramBot telegramBot, TableReservationService tableReservationService) {
        this.telegramBot = telegramBot;
        this.tableReservationService = tableReservationService;
    }

    public void handleBooking(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        tableReservationService.createReservation(chatId);
        telegramBot.sendMessage(chatId, "✅ Стол успешно забронирован!");
    }
}