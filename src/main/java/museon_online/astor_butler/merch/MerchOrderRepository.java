package museon_online.astor_butler.merch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MerchOrderRepository extends JpaRepository<MerchOrder, UUID> {

    List<MerchOrder> findByUserId(UUID userId);
}