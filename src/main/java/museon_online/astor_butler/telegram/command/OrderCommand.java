package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.telegram.button.OrderButton;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/order")
@RequiredArgsConstructor
public class OrderCommand implements BotCommand {

    private final TelegramBot telegramBot;
    private final OrderButton orderButton;

    @Override
    public String getCommand() {
        return "/order";
    }

    @Override
    public String getDescription() {
        return "Оформить заказ (стол, мерч, благотворительность)";
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        telegramBot.sendMessage(chatId, "Выберите тип заказа:", orderButton.createOrderButton());
    }
}
