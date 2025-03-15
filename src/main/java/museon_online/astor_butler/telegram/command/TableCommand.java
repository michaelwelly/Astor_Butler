package museon_online.astor_butler.telegram.command;

import museon_online.astor_butler.telegram.button.TableButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

@Component
public class TableCommand implements BotCommand {

    private final TableButton tableButton;

    public TableCommand(TableButton tableButton) {
        this.tableButton = tableButton;
    }

    @Override
    public String execute(Update update) {
        return "📅 Переход к выбору стола...";
    }

    @Override
    public String getCommand() {
        return "/book_table";
    }

}
