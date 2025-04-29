package museon_online.astor_butler.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    private Long userId;

    private BigDecimal amount;

    private LocalDateTime timestamp;

    // геттеры и сеттеры
}
