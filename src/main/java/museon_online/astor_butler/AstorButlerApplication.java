package museon_online.astor_butler;

import io.github.cdimascio.dotenv.Dotenv;
import museon_online.astor_butler.config.TelegramOAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramOAuthProperties.class)
public class AstorButlerApplication {

	static {
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
	}

	public static void main(String[] args) {
		SpringApplication.run(AstorButlerApplication.class, args);
	}

}
