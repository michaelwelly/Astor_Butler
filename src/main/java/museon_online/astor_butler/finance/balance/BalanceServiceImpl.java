package museon_online.astor_butler.finance.balance;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.finance.transaction.OperationType;
import museon_online.astor_butler.finance.transaction.TransactionLog;
import museon_online.astor_butler.finance.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BigDecimal getBalance(UUID userId, WalletType type) {
        return balanceRepository.findByUserIdAndType(userId, type)
                .map(UserBalance::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public void charge(UUID userId, BigDecimal amount, WalletType type, String context) {
        UserBalance balance = balanceRepository.findByUserIdAndType(userId, type)
                .orElseThrow(() -> new IllegalArgumentException("Баланс не найден для пользователя: " + userId));

        if (balance.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Недостаточно средств на балансе");
        }

        balance.setBalance(balance.getBalance().subtract(amount));
        balanceRepository.save(balance);

        log(userId, type, amount, context, OperationType.CHARGE);
    }

    @Override
    @Transactional
    public void add(UUID userId, BigDecimal amount, WalletType type, String context) {
        UserBalance balance = balanceRepository.findByUserIdAndType(userId, type)
                .orElseGet(() -> {
                    UserBalance b = new UserBalance();
                    b.setId(UUID.randomUUID());
                    b.setUserId(userId);
                    b.setType(type);
                    b.setBalance(BigDecimal.ZERO);
                    return b;
                });

        balance.setBalance(balance.getBalance().add(amount));
        balanceRepository.save(balance);

        log(userId, type, amount, context, OperationType.ADD);
    }

    private void log(UUID userId, WalletType type, BigDecimal amount, String context, OperationType opType) {
        TransactionLog log = new TransactionLog();
        log.setId(UUID.randomUUID());
        log.setUserId(userId);
        log.setType(type);
        log.setAmount(amount);
        log.setOperation(opType);
        log.setContext(context);
        log.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(log);
    }
}