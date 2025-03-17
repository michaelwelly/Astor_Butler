package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class BalanceHandler {

    public String handleBalanceCheck() {
        int balance = 1000; // Логика получения баланса из сервиса
        return "💰 Ваш баланс: " + balance + " звезд";
    }
}
