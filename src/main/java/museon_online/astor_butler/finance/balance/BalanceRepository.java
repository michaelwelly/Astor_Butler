package museon_online.astor_butler.finance.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BalanceRepository extends JpaRepository<UserBalance, UUID> {

    Optional<UserBalance> findByUserIdAndType(UUID userId, WalletType type);

}
