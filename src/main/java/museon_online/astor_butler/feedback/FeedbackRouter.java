
package museon_online.astor_butler.feedback;

import museon_online.astor_butler.central.DomainRouter;
import museon_online.astor_butler.telegram.utils.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;
import org.springframework.stereotype.Component;

@Component
public class FeedbackRouter implements DomainRouter {

    @Override
    public boolean canHandle(String command) {
        return "/feedback".equals(command);
    }

    @Override
    public BotResponse route(String command, CommandContext context) {
        return new BotResponse("💬 Оставьте ваш отзыв, пожалуйста:");
    }
}