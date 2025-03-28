package museon_online.astor_butler.table;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.slot.SlotType;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantTable {

    @Id
    private UUID id;

    private String name;

    private int capacity;

    @Enumerated(EnumType.STRING)
    private SlotType type;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}