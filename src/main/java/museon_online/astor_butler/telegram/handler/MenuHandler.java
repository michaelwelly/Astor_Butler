package museon_online.astor_butler.telegram.handler;

import org.springframework.stereotype.Component;

@Component
public class MenuHandler {

    public String handleMenu() {
        // Здесь будет логика получения и отображения меню
        return "🍽️ Вот наше меню: Пицца, Бургер, Суши...";
    }
}
