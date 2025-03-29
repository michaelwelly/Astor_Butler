package museon_online.astor_butler.telegram.fsm;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionManager {

    private final Map<Long, UserSession> sessions = new ConcurrentHashMap<>();

    public UserSession getOrCreateSession(Long userId) {
        return sessions.computeIfAbsent(userId, id -> new UserSession());
    }

    public void clearSession(Long userId) {
        sessions.remove(userId);
    }
}