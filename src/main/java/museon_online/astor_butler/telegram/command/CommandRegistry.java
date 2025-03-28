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
                if (annotation != null) {
                    commandMap.put(annotation.value(), (BotCommand) bean);
                }
            }
        }

        BotCommand startCommand = commandMap.get("/start");
        if (startCommand != null) {
            commandMap.put("start_command", startCommand);
        }
    }

    public BotResponse executeCommand(String command, Update update) {
        // Получаем Telegram ID
        String telegramId = update.getMessage().getFrom().getId().toString();

        // Ищем пользователя
        User user = userService.findUserByTelegramId(telegramId);
        if (user == null) {
            return new BotResponse("❌ Пользователь не найден");
        }

        // Проверка на наличие номера телефона
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            return new BotResponse("🚫 У вас нет доступа к этой команде. Добавьте номер телефона.");
        }

        // Поиск и выполнение команды
        BotCommand botCommand = commandMap.get(command);
        if (botCommand != null) {
            return botCommand.execute(update);
        } else {
            return new BotResponse("🤷‍♂️ Неизвестная команда.");
        }
    }
}
