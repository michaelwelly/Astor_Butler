package museon_online.astor_butler.config;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.telegram.command.CommandRegistry;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String botToken;
    private final CommandRegistry commandRegistry;
    private final TelegramExceptionHandler exceptionHandler;

    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(commandRegistry, exceptionHandler, botToken);
        exceptionHandler.setBot(bot);
        return bot;
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
    }
}
