package museon_online.astor_butler.auction;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/{id}/start")
    public void startAuction(@PathVariable Long id) {
        auctionService.startAuction(id);
    }

    @PostMapping("/{id}/bid")
    public void placeBid(@PathVariable Long id, @RequestParam Long userId, @RequestParam BigDecimal bidAmount) {
        auctionService.placeBid(id, userId, bidAmount);
    }

    @GetMapping("/{id}/history")
    public List<Bid> getBidHistory(@PathVariable Long id) {
        return auctionService.getBidHistory(id);
    }

    @PostMapping("/{id}/finish")
    public void finishAuction(@PathVariable Long id) {
        auctionService.finishAuction(id);
    }
}
