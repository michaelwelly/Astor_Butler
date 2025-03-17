package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class FeedbackHandler {

    public String handleFeedback(String feedback) {
        // Логика обработки фидбека
        return "Спасибо за ваш отзыв! 🙏";
    }
}
