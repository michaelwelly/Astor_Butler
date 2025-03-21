package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@TelegramCommand("/balance")
@RequiredArgsConstructor
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
