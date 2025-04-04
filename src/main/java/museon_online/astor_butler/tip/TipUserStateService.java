package museon_online.astor_butler.tip;

public interface TipUserStateService {

    TipState getState(Long telegramId);

    void setState(Long telegramId, TipState state);

    void clear(Long telegramId);

    TipUserContext getContext(Long telegramId);

    void setContext(Long telegramId, TipUserContext context);

}
