package museon_online.astor_butler.auction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionIdOrderByTimestampAsc(Long auctionId);
}