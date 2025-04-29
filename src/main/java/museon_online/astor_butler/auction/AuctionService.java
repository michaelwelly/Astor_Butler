package museon_online.astor_butler.auction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public AuctionService(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    @Transactional
    public void startAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        auction.setStatus(AuctionStatus.RUNNING);
        auction.setStartTime(LocalDateTime.now());
        auction.setCurrentPrice(auction.getStartPrice());
        auction.setMinStep(auction.getStartPrice().multiply(BigDecimal.valueOf(0.1)));
        auctionRepository.save(auction);
    }

    @Transactional
    public void placeBid(Long auctionId, Long userId, BigDecimal bidAmount) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        if (!auction.getStatus().equals(AuctionStatus.RUNNING)) {
            throw new IllegalStateException("Auction is not running");
        }
        if (bidAmount.compareTo(auction.getCurrentPrice().add(auction.getMinStep())) < 0) {
            throw new IllegalArgumentException("Bid must be at least 10% higher than current price");
        }

        Bid bid = new Bid();
        bid.setAuction(auction);
        bid.setUserId(userId);
        bid.setAmount(bidAmount);
        bid.setTimestamp(LocalDateTime.now());
        bidRepository.save(bid);

        auction.setCurrentPrice(bidAmount);
        auction.setCurrentWinnerUserId(userId);
        auction.getBidHistory().add(bid);
        auctionRepository.save(auction);
    }

    public List<Bid> getBidHistory(Long auctionId) {
        return bidRepository.findByAuctionIdOrderByTimestampAsc(auctionId);
    }

    @Transactional
    public void finishAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow();
        auction.setStatus(AuctionStatus.ENDED);
        auction.setEndTime(LocalDateTime.now());
        auctionRepository.save(auction);
    }
}