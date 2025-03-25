package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.utils.TelegramParser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfishaHandler {

    private final TelegramParser telegramParser;

    public String handleAfisha() {
        return telegramParser.parseEvents();
    }
}
