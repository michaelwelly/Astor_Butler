package museon_online.astor_butler.telegram;

import museon_online.astor_butler.telegram.command.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commandMap = new HashMap<>();

    @Autowired
    public CommandRegistry(
            StartCommand startCommand,
            BalanceCommand balanceCommand,
            MenuCommand menuCommand,
            TableCommand tableCommand,
            SlotCommand slotCommand
    ) {
        commandMap = Map.of(
                "/start", startCommand,
                "/balance", balanceCommand,
                "/menu", menuCommand,
                "/book_table", tableCommand,
                "/slots", slotCommand
        );
    }

    public String executeCommand(String command, Update update) {
        BotCommand botCommand = commandMap.get(command);
        if (botCommand != null) {
            return botCommand.execute(update);
        } else {
            return "Неизвестная команда 🤷‍♂️";
        }
    }
}
