package museon_online.astor_butler.feedback;

import museon_online.astor_butler.feedback.FeedbackState;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FeedbackUserState {

    private final Map<Long, FeedbackState> stateMap = new ConcurrentHashMap<>();

    public void set(Long userId, FeedbackState state) {
        stateMap.put(userId, state);
    }

    public boolean is(Long userId, FeedbackState state) {
        return state.equals(stateMap.get(userId));
    }

    public void clear(Long userId) {
        stateMap.remove(userId);
    }
}