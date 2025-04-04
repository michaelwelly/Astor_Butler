package museon_online.astor_butler.finance.transaction;

import jakarta.persistence.*;
import lombok.*;
import museon_online.astor_butler.finance.balance.WalletType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction_log")
public class TransactionLog {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", nullable = false)
    private OperationType operation;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String context;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        this.createdAt = LocalDateTime.now();
    }
}
