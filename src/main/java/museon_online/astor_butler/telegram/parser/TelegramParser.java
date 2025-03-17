package museon_online.astor_butler.telegram.parser;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TelegramParser {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.channel.username}")
    private String channelUsername;

    private static final Pattern DATE_PATTERN = Pattern.compile(
            "\\b(\\d{1,2}\\s[А-Яа-я]+)\\b"
    );

    private final RestTemplate restTemplate = new RestTemplate();

    private final Cache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(10)
            .build();

    public String parseEvents() {
        String cachedEvents = cache.getIfPresent("afisha");
        if (cachedEvents != null) {
            return cachedEvents;
        }

        String url = "https://api.telegram.org/bot" + botToken +
                "/getUpdates?chat_id=" + channelUsername + "&limit=5";

        try {
            String response = restTemplate.getForObject(url, String.class);
            if (response != null && containsDate(response)) {
                LocalDateTime eventDate = extractEventDate(response);
                long duration = Duration.between(LocalDateTime.now(), eventDate).toMinutes();

                if (duration > 0) {
                    cache.put("afisha", response);
                    cache.policy().expireAfterWrite().ifPresent(policy ->
                            policy.setExpiresAfter(duration, TimeUnit.MINUTES));
                }

                return "🎭 Афиша:\n" + response;
            } else {
                return "🚫 Похоже, это реклама — пропускаем.";
            }
        } catch (Exception e) {
            return "❌ Ошибка при парсинге канала: " + e.getMessage();
        }
    }

    private boolean containsDate(String message) {
        Matcher matcher = DATE_PATTERN.matcher(message);
        return matcher.find();
    }

    private LocalDateTime extractEventDate(String message) {
        Matcher matcher = DATE_PATTERN.matcher(message);
        if (matcher.find()) {
            String date = matcher.group();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));
            return LocalDate.parse(date, formatter).atStartOfDay();
        }
        return LocalDateTime.now().plusMinutes(10);
    }
}
