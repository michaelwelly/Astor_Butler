package museon_online.astor_butler.poster;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PosterParserService {

    private static final Pattern POSTER_PATTERN = Pattern.compile("^(\\d{1,2})\\s+([А-Яа-я]+)\\s*\\|\\s*(.+)$");

    public boolean isValid(String message) {
        return POSTER_PATTERN.matcher(message).matches();
    }

    public EventPosterDto parse(String rawText) {
        Matcher matcher = POSTER_PATTERN.matcher(rawText);
        if (!matcher.matches()) throw new IllegalArgumentException("Invalid format for poster");

        int day = Integer.parseInt(matcher.group(1));
        String monthName = matcher.group(2).toLowerCase();
        String title = matcher.group(3);

        int month = getMonthFromRussian(monthName);
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), month, day);

        return new EventPosterDto(date, title, rawText, "aeris_gastrobar");
    }

    private int getMonthFromRussian(String name) {
        return switch (name) {
            case "января" -> 1;
            case "февраля" -> 2;
            case "марта" -> 3;
            case "апреля" -> 4;
            case "мая" -> 5;
            case "июня" -> 6;
            case "июля" -> 7;
            case "августа" -> 8;
            case "сентября" -> 9;
            case "октября" -> 10;
            case "ноября" -> 11;
            case "декабря" -> 12;
            default -> throw new IllegalArgumentException("Неизвестный месяц: " + name);
        };
    }
}
