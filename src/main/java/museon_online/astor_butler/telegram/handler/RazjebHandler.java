package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class RazjebHandler {

    public String handleRazjeb() {
        int balance = 777;
        if (balance >= 777) {
            return "🔥 Управляющий готов к разъёбу! 🚀";
        } else {
            return "😎 Недостаточно звёзд для разъёба!";
        }
    }
}
