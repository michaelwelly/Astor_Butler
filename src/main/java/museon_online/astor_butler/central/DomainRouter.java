package museon_online.astor_butler.central;

import museon_online.astor_butler.telegram.command.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;

public interface DomainRouter {
    boolean canHandle(String command);
    BotResponse route(String command, CommandContext context);
}