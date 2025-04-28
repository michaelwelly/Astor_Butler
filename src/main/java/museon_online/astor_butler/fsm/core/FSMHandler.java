package museon_online.astor_butler.fsm.core;

import museon_online.astor_butler.telegram.utils.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;

public interface FSMHandler<T extends Enum<T>> {
    boolean supports(CommandContext context);
    BotResponse handle(CommandContext context, T currentState);
}