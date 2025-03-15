package museon_online.astor_butler.config;

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
}
