package museon_online.astor_butler.charity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharityOrderService {

    private final CharityOrderRepository charityOrderRepository;

    public CharityOrder createCharityOrder(CharityOrder order) {
        order.setStatus(CharityOrderStatus.PENDING);
        return charityOrderRepository.save(order);
    }

    public List<CharityOrder> getCharityOrdersByUser(UUID userId) {
        return charityOrderRepository.findByUserId(userId);
    }

    public void completeCharityOrder(UUID orderId) {
        CharityOrder order = charityOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
        order.setStatus(CharityOrderStatus.COMPLETED);
        charityOrderRepository.save(order);
    }

    public void deleteCharityOrder(UUID orderId) {
        charityOrderRepository.deleteById(orderId);
    }
}