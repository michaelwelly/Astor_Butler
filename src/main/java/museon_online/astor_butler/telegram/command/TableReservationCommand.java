package museon_online.astor_butler.telegram.command;

import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class TableReservationCommand implements TelegramCommand {

    private final TelegramBot telegramBot;

    @Autowired
    public TableReservationCommand(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public String execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(InlineKeyboardButton.builder()
                .text("Забронировать стол")
                .callbackData("/book_table")
                .build());

        markup.setKeyboard(List.of(row));

        telegramBot.sendMessage(chatId, "🍽 Выберите стол для бронирования:", markup);
        return "Ожидание выбора стола...";
    }
}