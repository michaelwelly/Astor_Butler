package museon_online.astor_butler.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/balance")
public class BalanceCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "/balance";
    }

    @Override
    public String execute(Update update) {
        int balance = 1000; // Здесь получаем баланс из UserService
        return "Ваш баланс: " + balance + " 🌟";
    }
}
