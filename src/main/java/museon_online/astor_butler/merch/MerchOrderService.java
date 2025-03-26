package museon_online.astor_butler.merch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchOrderService {

    private final MerchOrderRepository merchOrderRepository;

    public MerchOrder createMerchOrder(MerchOrder order) {
        order.setStatus(MerchOrderStatus.PENDING);
        return merchOrderRepository.save(order);
    }

    public List<MerchOrder> getMerchOrdersByUser(UUID userId) {
        return merchOrderRepository.findByUserId(userId);
    }

    public void completeMerchOrder(UUID orderId) {
        MerchOrder order = merchOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order not found"));
        order.setStatus(MerchOrderStatus.COMPLETED);
        merchOrderRepository.save(order);
    }

    public void deleteMerchOrder(UUID orderId) {
        merchOrderRepository.deleteById(orderId);
    }
}