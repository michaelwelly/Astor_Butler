package museon_online.astor_butler.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "telegram.oauth")
public class TelegramOAuthProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;

    public TelegramOAuthProperties() {
        Dotenv dotenv = Dotenv.load();
        this.clientId = dotenv.get("TELEGRAM_CLIENT_ID");
        this.clientSecret = dotenv.get("TELEGRAM_CLIENT_SECRET");
        this.redirectUri = dotenv.get("TELEGRAM_REDIRECT_URI");
    }
}
