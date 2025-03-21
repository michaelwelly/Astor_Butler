package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.handler.AfishaHandler;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@TelegramCommand("/afisha")
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
