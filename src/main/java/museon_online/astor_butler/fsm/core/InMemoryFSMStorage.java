package museon_online.astor_butler.fsm.core;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryFSMStorage implements FSMStorage {

    private final Map<Long, Enum<?>> stateMap = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getState(Long userId) {
        return (T) stateMap.get(userId);
    }

    @Override
    public <T extends Enum<T>> void setState(Long userId, T state) {
        stateMap.put(userId, state);
    }

    @Override
    public void clearState(Long userId) {
        stateMap.remove(userId);
    }
}