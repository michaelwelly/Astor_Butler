package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/order")
@RequiredArgsConstructor
public class OrderCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "/order";
    }

    @Override
    public String execute(Update update) {
        return "Для оформления заказа используйте команду в формате:\n/order [название] [кол-во] 🌟";
    }
}
