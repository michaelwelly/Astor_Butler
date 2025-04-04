package museon_online.astor_butler.finance.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentSessionRepository extends JpaRepository<PaymentSession, UUID> {

    Optional<PaymentSession> findByUserIdAndStatus(UUID userId, PaymentStatus status);

}
