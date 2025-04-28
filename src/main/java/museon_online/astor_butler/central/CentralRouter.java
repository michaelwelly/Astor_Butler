package museon_online.astor_butler.central;

import java.util.List;

import museon_online.astor_butler.fsm.core.FSMRouter;
import museon_online.astor_butler.telegram.utils.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class CentralRouter {

    private final List<DomainRouter> domainRouters;
    private final FSMRouter fsmRouter;

    public CentralRouter(List<DomainRouter> domainRouters, FSMRouter fsmRouter) {
        this.domainRouters = domainRouters;
        this.fsmRouter = fsmRouter;
    }

    public BotResponse route(CommandContext ctx) {
        BotResponse fsmResponse = fsmRouter.route(ctx);
        if (fsmResponse != null) return fsmResponse;

        String command = ctx.getCommand();
        for (DomainRouter router : domainRouters) {
            if (router.canHandle(command)) {
                return router.route(command, ctx);
            }
        }
        return new BotResponse("⚠️ Неизвестная команда: " + command);
    }
}