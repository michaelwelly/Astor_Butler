package museon_online.astor_butler.finance.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionLog, UUID> {
}
