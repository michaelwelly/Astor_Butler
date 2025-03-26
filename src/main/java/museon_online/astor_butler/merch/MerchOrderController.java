package museon_online.astor_butler.merch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merch-orders")
@RequiredArgsConstructor
public class MerchOrderController {

    private final MerchOrderService merchOrderService;

    @PostMapping
    public ResponseEntity<MerchOrder> createOrder(@RequestBody MerchOrder order) {
        MerchOrder savedOrder = merchOrderService.createMerchOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MerchOrder>> getOrdersByUser(@PathVariable UUID userId) {
        List<MerchOrder> orders = merchOrderService.getMerchOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<MerchOrder> completeOrder(@PathVariable UUID id) {
        merchOrderService.completeMerchOrder(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        merchOrderService.deleteMerchOrder(id);
        return ResponseEntity.noContent().build();
    }
}