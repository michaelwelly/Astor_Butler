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

        String platform = "TELEGRAM"; // может быть определено динамически позже
        String locale = "ru";         // временно зафиксировано, можно брать из update.getFrom().getLanguageCode()
        String aiIntent = null;       // зарезервировано для AI-интеграции

        return new CommandContext(
                command,
                userId,
                chatId,
                platform,
                locale,
                aiIntent,
                update.raw()
        );
    }
}