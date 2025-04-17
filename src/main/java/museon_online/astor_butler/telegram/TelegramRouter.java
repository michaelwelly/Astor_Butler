package museon_online.astor_butler.telegram;

import museon_online.astor_butler.telegram.context.CommandContext;
import museon_online.astor_butler.telegram.context.CommandContextBuilder;

import museon_online.astor_butler.telegram.utils.AstorUpdate;
import museon_online.astor_butler.telegram.command.BotResponse;
import museon_online.astor_butler.central.CentralRouter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramRouter {

    private final CentralRouter centralRouter;
    private final CommandContextBuilder contextBuilder;

    public TelegramRouter(CentralRouter centralRouter, CommandContextBuilder contextBuilder) {
        this.centralRouter = centralRouter;
        this.contextBuilder = contextBuilder;
    }

    public BotResponse route(Update update) {
        // Оборачиваем Update в наш удобный DTO
        AstorUpdate astorUpdate = new AstorUpdate(update);

        // Собираем контекст с пользователем, источником и мета-инфой
        CommandContext context = contextBuilder.build(astorUpdate);

        // Достаём команду из текста или callback
        String command = context.getCommand();
        if (command == null || command.isBlank()) {
            return new BotResponse("🤷‍♂️ Команда не распознана");
        }

        // Передаём в центральный маршрутизатор
        return centralRouter.route(context);
    }
}

