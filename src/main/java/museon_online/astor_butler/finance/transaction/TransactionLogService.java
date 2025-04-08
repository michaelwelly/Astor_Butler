package museon_online.astor_butler.finance.transaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.finance.balance.WalletType;
import museon_online.astor_butler.finance.reward.RewardPeriodType;
import museon_online.astor_butler.finance.reward.RewardReason;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionLogService {

    private final TransactionRepository transactionRepository;

    public boolean hasReceivedReward(UUID userId, RewardReason reason) {
        String context = "REWARD:" + reason.name();
        return transactionRepository.existsByUserIdAndContext(userId, context);
    }

    public int countRewardsInPeriod(UUID userId, RewardReason reason, RewardPeriodType periodType) {
        String context = "REWARD:" + reason.name();
        LocalDateTime since = switch (periodType) {
            case DAILY -> LocalDateTime.now().minusDays(1);
            case MONTHLY -> LocalDateTime.now().minusMonths(1);
            case YEARLY -> LocalDateTime.now().minusYears(1);
            case SEASONAL -> LocalDateTime.now().minusMonths(3);
            case EVENT -> LocalDateTime.now().minusDays(7); // условно
        };
        return transactionRepository.countByUserIdAndContextAndCreatedAtAfter(userId, context, since);
    }

    @Transactional
    public void logReward(UUID userId, WalletType wallet, OperationType operation, int stars, String context) {
        TransactionLog log = TransactionLog.builder()
                .userId(userId)
                .type(wallet)
                .operation(operation)
                .amount(BigDecimal.valueOf(stars))
                .context(context)
                .build();
        transactionRepository.save(log);
    }
}