package museon_online.astor_butler.telegram.command;

import museon_online.astor_butler.telegram.button.MenuButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

@Component
public class MenuCommand implements BotCommand {

    private final MenuButton menuButton;

    public MenuCommand(MenuButton menuButton) {
        this.menuButton = menuButton;
    }

    @Override
    public String execute(Update update) {
        // Логика показа меню — заглушка на данный момент
        return "📋 Вот наше меню!";
    }

    @Override
    public String getCommand() {
        return "/menu";
    }

}
