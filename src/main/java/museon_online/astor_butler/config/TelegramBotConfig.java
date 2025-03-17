package museon_online.astor_butler.config;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.service.TelegramOAuthService;
import museon_online.astor_butler.service.UserService;
import museon_online.astor_butler.telegram.TelegramBot;
import museon_online.astor_butler.telegram.CommandRegistry;
import museon_online.astor_butler.telegram.button.*;
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
    public TelegramOAuthService telegramOAuthService(UserService userService, TelegramExceptionHandler exceptionHandler, TelegramBot telegramBot) {
        return new TelegramOAuthService(userService, exceptionHandler, telegramBot);
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        return new DefaultBotOptions();
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
