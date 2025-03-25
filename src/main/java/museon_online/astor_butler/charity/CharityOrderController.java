package museon_online.astor_butler.charity;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/charity-orders")
@RequiredArgsConstructor
public class CharityOrderController {

    private final CharityOrderService charityOrderService;

    @PostMapping
    public ResponseEntity<CharityOrder> createOrder(@RequestBody CharityOrder order) {
        CharityOrder savedOrder = charityOrderService.createCharityOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CharityOrder>> getOrdersByUser(@PathVariable UUID userId) {
        List<CharityOrder> orders = charityOrderService.getCharityOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<CharityOrder> completeOrder(@PathVariable UUID id) {
        charityOrderService.completeCharityOrder(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        charityOrderService.deleteCharityOrder(id);
        return ResponseEntity.noContent().build();
    }
}