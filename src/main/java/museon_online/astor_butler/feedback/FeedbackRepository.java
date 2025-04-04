package museon_online.astor_butler.feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    boolean existsByUserIdAndRewardGivenIsTrue(UUID userId);

    Optional<Feedback> findTopByUserIdOrderByCreatedAtDesc(UUID userId);

}
