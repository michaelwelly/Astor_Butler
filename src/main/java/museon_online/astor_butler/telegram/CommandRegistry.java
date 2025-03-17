package museon_online.astor_butler.telegram;

import museon_online.astor_butler.model.User;
import museon_online.astor_butler.service.UserService;
import museon_online.astor_butler.telegram.command.*;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commandMap;
    private final UserService userService;

    public CommandRegistry(
            StartCommand startCommand,
            BalanceCommand balanceCommand,
            MenuCommand menuCommand,
            TableCommand tableCommand,
            SlotCommand slotCommand,
            FeedbackCommand feedbackCommand,
            OrderCommand orderCommand,
            RazjebCommand razjebCommand,
            AfishaCommand afishaCommand,
            UserService userService) {

        this.userService = userService;

        commandMap = Map.of(
                "/start", startCommand,
                "/balance", balanceCommand,
                "/menu", menuCommand,
                "/book_table", tableCommand,
                "/slots", slotCommand,
                "/feedback", feedbackCommand,
                "/order", orderCommand,
                "/razjeb", razjebCommand,
                "/afisha", afishaCommand
        );
    }

    public String executeCommand(String command, Update update) {
        User user = userService.findByTelegramId(update.getMessage().getFrom().getId().toString());
        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        if (user.isRequiresPhone()) {
            return "🚫 У вас нет доступа к этой команде.";
        }

        BotCommand botCommand = commandMap.get(command);
        if (botCommand != null) {
            return botCommand.execute(update);
        } else {
            return "Неизвестная команда 🤷‍♂️";
        }
    }

}

