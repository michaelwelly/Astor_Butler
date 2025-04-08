package museon_online.astor_butler.finance.reward;


import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.finance.balance.WalletType;
import museon_online.astor_butler.finance.transaction.OperationType;
import museon_online.astor_butler.finance.transaction.TransactionLogService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardsEngine {

    private final RewardPolicyRepository policyRepository;
    private final TransactionLogService transactionLogService;

    public boolean reward(UUID userId, RewardReason reason) {
       RewardPolicy policy = policyRepository.findById(reason)
                .orElseThrow(() -> new IllegalArgumentException("Reward policy not defined for: " + reason));

        if (!policy.isRepeatable() && transactionLogService.hasReceivedReward(userId, reason)) {
            return false;
        }

        if (policy.getMaxPerPeriod() != null && policy.getPeriodType() != null) {
            int count = transactionLogService.countRewardsInPeriod(userId, reason, policy.getPeriodType());
            if (count >= policy.getMaxPerPeriod()) {
                return false;
            }
        }

        transactionLogService.logReward(
                userId,
                WalletType.STARS,
                OperationType.GIFT,
                policy.getStars(),
                "REWARD:" + reason.name()
        );

        return true;
    }
}