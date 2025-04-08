package museon_online.astor_butler.finance.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionLog, UUID> {
    boolean existsByUserIdAndContext(UUID userId, String context);

    int countByUserIdAndContextAndCreatedAtAfter(UUID userId, String context, LocalDateTime createdAt);
}
