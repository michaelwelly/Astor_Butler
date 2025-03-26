package museon_online.astor_butler.telegram.command;

import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commandMap = new HashMap<>();
    private final UserService userService;

    public CommandRegistry(ApplicationContext applicationContext, UserService userService) {
        this.userService = userService;

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(TelegramCommand.class);
        for (Object bean : beans.values()) {
            if (bean instanceof BotCommand) {
                TelegramCommand annotation = bean.getClass().getAnnotation(TelegramCommand.class);
                commandMap.put(annotation.value(), (BotCommand) bean);
            }
        }
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
