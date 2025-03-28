package museon_online.astor_butler.integration;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class YandexWorkingHoursParser {

    private static final Map<String, DayOfWeek> dayMap = Map.ofEntries(
            Map.entry("пн", DayOfWeek.MONDAY),
            Map.entry("вт", DayOfWeek.TUESDAY),
            Map.entry("ср", DayOfWeek.WEDNESDAY),
            Map.entry("чт", DayOfWeek.THURSDAY),
            Map.entry("пт", DayOfWeek.FRIDAY),
            Map.entry("сб", DayOfWeek.SATURDAY),
            Map.entry("вс", DayOfWeek.SUNDAY)
    );

    public static Map<DayOfWeek, TimeRange> parseHours(String hoursText) {
        Map<DayOfWeek, TimeRange> schedule = new EnumMap<>(DayOfWeek.class);
        Pattern entryPattern = Pattern.compile("([а-яА-ЯёЁ–]+)\\s+(\\d{1,2}:\\d{2})–(\\d{1,2}:\\d{2})");

        for (String entry : hoursText.split(",\\s*")) {
            Matcher matcher = entryPattern.matcher(entry.toLowerCase());
            if (!matcher.find()) continue;

            String daysPart = matcher.group(1);
            LocalTime start = LocalTime.parse(matcher.group(2));
            LocalTime end = LocalTime.parse(matcher.group(3));

            List<DayOfWeek> days = expandDays(daysPart);
            for (DayOfWeek day : days) {
                schedule.put(day, new TimeRange(start, end));
            }
        }
        return schedule;
    }

    private static List<DayOfWeek> expandDays(String text) {
        String[] parts = text.split("–");
        if (parts.length == 2) {
            DayOfWeek from = dayMap.get(parts[0]);
            DayOfWeek to = dayMap.get(parts[1]);
            if (from != null && to != null) {
                List<DayOfWeek> days = new ArrayList<>();
                for (int i = from.getValue(); ; i = i % 7 + 1) {
                    days.add(DayOfWeek.of(i));
                    if (DayOfWeek.of(i).equals(to)) break;
                }
                return days;
            }
        } else {
            DayOfWeek single = dayMap.get(text);
            if (single != null) return List.of(single);
        }
        return Collections.emptyList();
    }

    public record TimeRange(LocalTime start, LocalTime end) {}
}
