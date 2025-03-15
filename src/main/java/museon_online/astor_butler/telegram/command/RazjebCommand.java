package museon_online.astor_butler.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/razjeb")
public class RazjebCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "/razjeb";
    }

    @Override
    public String execute(Update update) {
        // Проверяем баланс пользователя перед выполнением
        int balance = 777; // Здесь нужно получить баланс из UserService
        if (balance >= 777) {
            return "Управляющий готов к разъёбу! 🚀🔥";
        } else {
            return "Недостаточно звёзд для разъёба! 😎";
        }
    }
}
