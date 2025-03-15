package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.Order;
import museon_online.astor_butler.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order createOrder(Order order) {
        if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Order amount must be greater than zero");
        }

        if (order.getStarsAmount() != null && order.getStarsAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (order.getUser().getStarsBalance().compareTo(order.getStarsAmount()) < 0) {
                throw new RuntimeException("Not enough stars to complete the order");
            }

            order.getUser().setStarsBalance(
                    order.getUser().getStarsBalance().subtract(order.getStarsAmount())
            );
        }

        return orderRepository.save(order);
    }

    public Order updateOrder(UUID id, Order updatedOrder) {
        Order order = getOrderById(id);

        if (updatedOrder.getAmount() != null) {
            if (updatedOrder.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Order amount must be greater than zero");
            }
            order.setAmount(updatedOrder.getAmount());
        }

        if (updatedOrder.getStarsAmount() != null) {
            if (updatedOrder.getStarsAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Stars amount must be greater than zero");
            }

            BigDecimal availableStars = order.getUser().getStarsBalance().add(
                    order.getStarsAmount() != null ? order.getStarsAmount() : BigDecimal.ZERO
            );

            if (availableStars.compareTo(updatedOrder.getStarsAmount()) < 0) {
                throw new RuntimeException("Not enough stars to update the order");
            }

            BigDecimal diff = updatedOrder.getStarsAmount().subtract(
                    order.getStarsAmount() != null ? order.getStarsAmount() : BigDecimal.ZERO
            );

            order.getUser().setStarsBalance(order.getUser().getStarsBalance().subtract(diff));
            order.setStarsAmount(updatedOrder.getStarsAmount());
        }

        if (updatedOrder.getStatus() != null) {
            order.setStatus(updatedOrder.getStatus());
        }

        return orderRepository.save(order);
    }


    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
