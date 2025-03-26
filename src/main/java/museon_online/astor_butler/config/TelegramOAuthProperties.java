package museon_online.astor_butler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.telegram.oauth")
public class TelegramOAuthProperties {

    private String clientId;
    private String redirectUri;

    public String getAuthUrl() {
        return "https://oauth.telegram.org/auth?bot_id=" + clientId +
                "&redirect_uri=" + redirectUri;
    }
}