package museon_online.astor_butler.feedback;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.slot.BookingSlot;
import museon_online.astor_butler.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String text;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookingSlot slot;

    @Column(name = "reward_given", nullable = false)
    private boolean rewardGiven = false;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
    }
}
