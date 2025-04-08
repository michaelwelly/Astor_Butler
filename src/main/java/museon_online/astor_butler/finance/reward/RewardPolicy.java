package museon_online.astor_butler.finance.reward;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.finance.reward.RewardPeriodType;
import museon_online.astor_butler.finance.reward.RewardReason;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reward_policy")
public class RewardPolicy {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, unique = true)
    private RewardReason reason;

    @Column(name = "stars", nullable = false)
    private int stars;

    @Column(name = "repeatable", nullable = false)
    private boolean repeatable;

    @Column(name = "max_per_period")
    private Integer maxPerPeriod; // null если неограниченно

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private RewardPeriodType periodType; // DAILY, MONTHLY, EVENT
}