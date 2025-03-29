package museon_online.astor_butler.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TimeRange {
    private LocalTime start;
    private LocalTime end;

    public static String toJson(Map<DayOfWeek, TimeRange> map) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, TimeRange> converted = new LinkedHashMap<>();
        map.forEach((k, v) -> converted.put(k.name(), v));
        try {
            return mapper.writeValueAsString(converted);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize schedule", e);
        }
    }

    public static Map<DayOfWeek, TimeRange> parseSchedule(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, TimeRange> raw = mapper.readValue(json,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, TimeRange.class));
            Map<DayOfWeek, TimeRange> result = new EnumMap<>(DayOfWeek.class);
            raw.forEach((k, v) -> result.put(DayOfWeek.valueOf(k), v));
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse schedule", e);
        }
    }
}
