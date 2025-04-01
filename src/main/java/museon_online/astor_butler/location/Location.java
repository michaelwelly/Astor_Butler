package museon_online.astor_butler.location;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.location.util.TimeRange;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private ZoneId timezone;

    @Lob
    private String scheduleJson;

    @Transient
    private Map<DayOfWeek, TimeRange> schedule;

    @PostLoad
    private void deserializeSchedule() {
        this.schedule = TimeRange.parseSchedule(scheduleJson);
    }

    @PrePersist
    @PreUpdate
    private void serializeSchedule() {
        this.scheduleJson = TimeRange.toJson(schedule);
    }
}
