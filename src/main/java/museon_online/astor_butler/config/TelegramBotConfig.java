package museon_online.astor_butler.config;

import museon_online.astor_butler.telegram.TelegramBot;
import museon_online.astor_butler.telegram.CommandRegistry;
import museon_online.astor_butler.telegram.button.MenuButton;
import museon_online.astor_butler.telegram.button.SlotButton;
import museon_online.astor_butler.telegram.button.TableButton;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    private final CommandRegistry commandRegistry;
    private final TelegramExceptionHandler exceptionHandler;

    public TelegramBotConfig(CommandRegistry commandRegistry, TelegramExceptionHandler exceptionHandler) {
        this.commandRegistry = commandRegistry;
        this.exceptionHandler = exceptionHandler;
    }

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(commandRegistry, exceptionHandler, botToken);
    }

    @Bean
    public MenuButton menuButton() {
        return new MenuButton();
    }

    @Bean
    public TableButton tableButton() {
        return new TableButton();
    }

    @Bean
    public SlotButton slotButton() {
        return new SlotButton();
    }

}
