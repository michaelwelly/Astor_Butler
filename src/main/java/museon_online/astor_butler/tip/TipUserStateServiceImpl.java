package museon_online.astor_butler.tip;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TipUserStateServiceImpl implements TipUserStateService {

    private final TipSessionRepository repository;

    @Override
    public TipState getState(Long telegramId) {
        return repository.findByTelegramId(telegramId)
                .map(TipSession::getState)
                .orElse(null);
    }

    @Override
    public void setState(Long telegramId, TipState state) {
        TipSession session = repository.findByTelegramId(telegramId)
                .orElseGet(() -> TipSession.builder()
                        .id(UUID.randomUUID())
                        .telegramId(telegramId)
                        .build());
        session.setState(state);
        repository.save(session);
    }

    @Override
    public void clear(Long telegramId) {
        repository.deleteByTelegramId(telegramId);
    }

    @Override
    public TipUserContext getContext(Long telegramId) {
        return repository.findByTelegramId(telegramId)
                .map(s -> new TipUserContext(s.getAmount()))
                .orElse(null);
    }

    @Override
    public void setContext(Long telegramId, TipUserContext context) {
        TipSession session = repository.findByTelegramId(telegramId)
                .orElseThrow();
        session.setAmount(context.getAmount());
        repository.save(session);
    }
}