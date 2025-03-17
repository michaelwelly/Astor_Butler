package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class OrderHandler {

    public String handleOrder(String item, int quantity) {
        // Логика оформления заказа
        return "✅ Заказ на " + quantity + " × " + item + " успешно оформлен!";
    }
}
