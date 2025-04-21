package museon_online.astor_butler.telegram.context;

import museon_online.astor_butler.telegram.utils.AstorUpdate;
import org.springframework.stereotype.Component;

@Component
public class CommandContextBuilderImpl implements CommandContextBuilder {

    @Override
    public CommandContext build(AstorUpdate update) {
        String command = update.text();
        Long userId = update.raw().getMessage() != null ? update.raw().getMessage().getFrom().getId() : null;
        Long chatId = update.chatId();

        if (command == null || userId == null || chatId == null) {
            throw new IllegalStateException("Недостаточно данных для построения CommandContext");
        }

        return CommandContext.builder()
                .command(command)
                .telegramUserId(userId)
                .chatId(chatId)
                .platform("TELEGRAM")
                .locale("ru")
                .aiIntent(null)
                .rawUpdate(update.raw())
                .build();
    }
}