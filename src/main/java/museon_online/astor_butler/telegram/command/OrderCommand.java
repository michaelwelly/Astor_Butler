package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.button.OrderButton;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@TelegramCommand(value = "/order", description = "Оформить заказ (стол, мерч, благотворительность)")
@RequiredArgsConstructor
public class OrderCommand implements BotCommand {

    private final TelegramBot telegramBot;
    private final OrderButton orderButton;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        telegramBot.sendMessageWithMarkup(chatId, "Выберите тип заказа:", orderButton.createOrderButton());    }

    @Override
    public String getCommand() {
        return "/order";
    }

    @Override
    public String getDescription() {
        return "Оформить заказ (стол, мерч, благотворительность)";
    }
}