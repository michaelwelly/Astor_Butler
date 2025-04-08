package museon_online.astor_butler.finance.reward;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardPolicyRepository extends JpaRepository<RewardPolicy, RewardReason> {
}