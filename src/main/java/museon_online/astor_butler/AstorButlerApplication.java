package museon_online.astor_butler;

import museon_online.astor_butler.config.TelegramOAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramOAuthProperties.class)
public class AstorButlerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AstorButlerApplication.class, args);
	}

}
