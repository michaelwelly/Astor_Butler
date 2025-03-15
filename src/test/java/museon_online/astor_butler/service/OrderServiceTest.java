package museon_online.astor_butler.service;

import museon_online.astor_butler.model.Order;
import museon_online.astor_butler.model.User;
import museon_online.astor_butler.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Order order;

    @BeforeEach
    void setUp() {
        user = new User(
                UUID.randomUUID(),
                "12345",
                "John",
                "Doe",
                "johndoe",
                "1234567890",
                BigDecimal.valueOf(50.0) // баланс звёзд
        );

        order = new Order(
                UUID.randomUUID(),
                user,
                LocalDate.now(),
                LocalTime.now(),
                "PENDING",
                BigDecimal.valueOf(100.0),
                BigDecimal.ZERO
        );
    }

    @Test
    void testCreateOrder_Successful() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(BigDecimal.valueOf(100.0), createdOrder.getAmount());
        assertEquals("PENDING", createdOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrder_Failed_ZeroAmount() {
        order.setAmount(BigDecimal.ZERO);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Order amount must be greater than zero", exception.getMessage());
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testCreateOrderWithStars_Successful() {
        order.setStarsAmount(BigDecimal.valueOf(20.0));
        user.setStarsBalance(BigDecimal.valueOf(50.0));

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(BigDecimal.valueOf(20.0), createdOrder.getStarsAmount());
        assertEquals(BigDecimal.valueOf(30.0), user.getStarsBalance());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testCreateOrderWithStars_Failed_NotEnoughStars() {
        order.setStarsAmount(BigDecimal.valueOf(100.0));
        user.setStarsBalance(BigDecimal.valueOf(50.0));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(order);
        });

        assertEquals("Not enough stars to complete the order", exception.getMessage());
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testGetOrderById_Successful() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(order.getId());

        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        verify(orderRepository, times(1)).findById(order.getId());
    }

    @Test
    void testGetOrderById_Failed_NotFound() {
        UUID invalidId = UUID.randomUUID();

        when(orderRepository.findById(invalidId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(invalidId);
        });

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(invalidId);
    }

    @Test
    void testUpdateOrder_Successful() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = new Order();
        updatedOrder.setAmount(BigDecimal.valueOf(200.0));
        updatedOrder.setStatus("CONFIRMED");

        Order result = orderService.updateOrder(order.getId(), updatedOrder);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200.0), result.getAmount());
        assertEquals("CONFIRMED", result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrderWithStars_Successful() {
        order.setStarsAmount(BigDecimal.valueOf(20.0));
        user.setStarsBalance(BigDecimal.valueOf(50.0));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = new Order();
        updatedOrder.setStarsAmount(BigDecimal.valueOf(10.0));

        Order result = orderService.updateOrder(order.getId(), updatedOrder);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(10.0), result.getStarsAmount());
        assertEquals(BigDecimal.valueOf(40.0), user.getStarsBalance());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateOrderWithStars_Failed_NotEnoughStars() {
        order.setStarsAmount(BigDecimal.valueOf(10.0));
        user.setStarsBalance(BigDecimal.valueOf(10.0));

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order updatedOrder = new Order();
        updatedOrder.setStarsAmount(BigDecimal.valueOf(20.0));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.updateOrder(order.getId(), updatedOrder);
        });

        assertEquals("Not enough stars to update the order", exception.getMessage());
        verify(orderRepository, never()).save(order);
    }

    @Test
    void testDeleteOrder_Successful() {
        doNothing().when(orderRepository).deleteById(order.getId());

        orderService.deleteOrder(order.getId());

        verify(orderRepository, times(1)).deleteById(order.getId());
    }
}
