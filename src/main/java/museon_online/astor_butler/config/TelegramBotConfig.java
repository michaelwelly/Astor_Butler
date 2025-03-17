package museon_online.astor_butler.config;

import museon_online.astor_butler.telegram.TelegramBot;
import museon_online.astor_butler.telegram.CommandRegistry;
import museon_online.astor_butler.telegram.button.*;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class TelegramBotConfig {

    @Value("${TELEGRAM_BOT_TOKEN}")
    private String botToken;

    private final CommandRegistry commandRegistry;
    private final TelegramExceptionHandler exceptionHandler;

    public TelegramBotConfig(CommandRegistry commandRegistry, TelegramExceptionHandler exceptionHandler) {
        this.commandRegistry = commandRegistry;
        this.exceptionHandler = exceptionHandler;
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
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

    @Bean
    public FeedbackButton feedbackButton() {
        return new FeedbackButton();
    }

    @Bean
    public OrderButton orderButton() {
        return new OrderButton();
    }

    @Bean
    public BalanceButton balanceButton() {
        return new BalanceButton();
    }

    @Bean
    public RazjebButton razjebButton() {
        return new RazjebButton();
    }

    @Bean
    public AfishaButton afishaButton() {
        return new AfishaButton();
    }

}
