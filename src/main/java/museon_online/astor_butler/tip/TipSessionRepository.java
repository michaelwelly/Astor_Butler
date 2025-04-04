package museon_online.astor_butler.tip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TipSessionRepository extends JpaRepository<TipSession, UUID> {

    Optional<TipSession> findByTelegramId(Long telegramId);

    void deleteByTelegramId(Long telegramId);

}
