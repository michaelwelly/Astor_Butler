package museon_online.astor_butler.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/start")
public class StartCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String execute(Update update) {
        return "Привет, я Астор Батлер! Чем могу помочь?";
    }
}
