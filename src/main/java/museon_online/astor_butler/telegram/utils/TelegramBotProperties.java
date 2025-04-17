package museon_online.astor_butler.telegram.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.bot")
@Component
@Getter
@Setter
public class TelegramBotProperties {
    private String token;
    private String username;
    private String webhookUrl;
    private boolean debugMode;
}
