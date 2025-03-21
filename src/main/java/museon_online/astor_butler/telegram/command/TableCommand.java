package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.button.TableButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

@TelegramCommand("/book_table")
@RequiredArgsConstructor
public class TableCommand implements BotCommand {

    private final TableButton tableButton;

    @Override
    public String execute(Update update) {
        return "📅 Переход к выбору стола...";
    }

    @Override
    public String getCommand() {
        return "/book_table";
    }

}
