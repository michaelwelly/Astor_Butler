package museon_online.astor_butler.central;

import java.util.List;
import museon_online.astor_butler.telegram.command.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class CentralRouter {

    private final List<DomainRouter> domainRouters;

    public CentralRouter(List<DomainRouter> domainRouters) {
        this.domainRouters = domainRouters;
    }

    public BotResponse route(CommandContext ctx) {
        String command = ctx.getCommand();
        for (DomainRouter router : domainRouters) {
            if (router.canHandle(command)) {
                return router.route(command, ctx);
            }
        }
        return new BotResponse("⚠️ Неизвестная команда: " + command);
    }
}