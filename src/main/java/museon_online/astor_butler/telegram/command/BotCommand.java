package museon_online.astor_butler.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {
    String getCommand();
    String execute(Update update);
}

