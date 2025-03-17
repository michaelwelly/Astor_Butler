package museon_online.astor_butler.telegram.handler;

import museon_online.astor_butler.telegram.command.MainMenuCommand;
import museon_online.astor_butler.telegram.command.StartCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartHandler {

    private final StartCommand startCommand;
    private final MainMenuCommand mainMenuCommand;

    public String handle(Update update) {
        String startResponse = startCommand.execute(update);

        String mainMenuResponse = mainMenuCommand.execute(update);

        return startResponse + "\n\n" + mainMenuResponse;
    }
}
