package museon_online.astor_butler.fsm.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeepSeekTransitionEngine implements FSMTransitionEngine {

    private final DeepSeekClient deepSeekClient;

    @Override
    public <T extends Enum<T>> T predictNext(T currentState, String userInput) {
        String modelResponse = deepSeekClient.askZenBuddha(currentState.name(), userInput);

        for (T possible : currentState.getDeclaringClass().getEnumConstants()) {
            if (possible.name().equalsIgnoreCase(modelResponse.trim())) {
                return possible;
            }
        }

        return currentState; // если не узнали — остаёмся на месте
    }
}