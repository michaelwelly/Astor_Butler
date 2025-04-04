package museon_online.astor_butler.finance.balance;

import java.math.BigDecimal;
import java.util.UUID;

public interface BalanceService {
    BigDecimal getBalance(UUID userId, WalletType type);
    void charge(UUID userId, BigDecimal amount, WalletType type, String context);
    void add(UUID userId, BigDecimal amount, WalletType type, String context);
}