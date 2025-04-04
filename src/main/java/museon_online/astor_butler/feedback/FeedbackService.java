package museon_online.astor_butler.feedback;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.user.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repository;

    public void saveFeedback(User user, String text) {
        Feedback feedback = Feedback.builder()
                .user(user)
                .text(text)
                .rewardGiven(false)
                .build();
        repository.save(feedback);
    }

    public boolean hasReceivedReward(UUID userId) {
        return repository.existsByUserIdAndRewardGivenIsTrue(userId);
    }

    public void markRewardGiven(UUID userId) {
        Optional<Feedback> optional = repository.findTopByUserIdOrderByCreatedAtDesc(userId);
        if (optional.isPresent()) {
            Feedback feedback = optional.get();
            feedback.setRewardGiven(true);
            repository.save(feedback);
        }
    }
}
