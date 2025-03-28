package museon_online.astor_butler.feedback;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public Feedback saveFeedback(User user, String text) {
        Feedback feedback = Feedback.builder()
                .user(user)
                .text(text)
                .build();
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByUser(User user) {
        return feedbackRepository.findByUser(user);
    }
}