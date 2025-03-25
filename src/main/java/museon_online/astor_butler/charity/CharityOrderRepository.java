package museon_online.astor_butler.charity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CharityOrderRepository extends JpaRepository<CharityOrder, UUID> {
    List<CharityOrder> findByUserId(UUID userId);
}