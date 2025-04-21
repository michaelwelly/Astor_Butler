package museon_online.astor_butler.fsm.core;

public interface FSMStorage {
    <T extends Enum<T>> T getState(Long userId);
    <T extends Enum<T>> void setState(Long userId, T state);
    void clearState(Long userId);
}