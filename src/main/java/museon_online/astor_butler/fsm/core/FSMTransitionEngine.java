package museon_online.astor_butler.fsm.core;

public interface FSMTransitionEngine {
    <T extends Enum<T>> T predictNext(T currentState, String userInput);
}