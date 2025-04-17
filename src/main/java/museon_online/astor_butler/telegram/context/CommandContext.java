package museon_online.astor_butler.telegram.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@RequiredArgsConstructor
public class CommandContext {

    private final String command;
    private final Long telegramUserId;
    private final String platform;
    private final String locale;
    private final String aiIntent;
    private final Update rawUpdate;
    private final Long chatId;
}