package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.handler.AfishaHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/afisha")
@Component
@RequiredArgsConstructor
public class AfishaCommand implements BotCommand {

    private final AfishaHandler afishaHandler;

    @Override
    public String getCommand() {
        return "/afisha";
    }

    @Override
    public String execute(Update update) {
        return afishaHandler.handleAfisha();
    }
}
