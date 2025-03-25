package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.table.TableReservationOrder;
import museon_online.astor_butler.merch.MerchOrder;
import museon_online.astor_butler.charity.CharityOrder;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderHandler {

    private final TelegramBot telegramBot;
    private final OrderService orderService;

    public void handleCallback(Update update) {
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        switch (data) {
            case "order_menu" -> openOrderMenu(chatId);
            case "order_table" -> handleTableOrder(chatId);
            case "order_merch" -> handleMerchOrder(chatId);
            case "order_charity" -> handleCharityOrder(chatId);
            case "back_to_main" -> handleBackToMainMenu(chatId);
            default -> telegramBot.sendTextMessage(chatId, "❌ Неизвестная команда!");
        }
    }

    // 📌 Открыть меню заказов
    private void openOrderMenu(Long chatId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        // Первая строка — Заказ стола и мерча
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(createButton("🍽 Заказ стола", "order_table"));
        row1.add(createButton("🛍 Заказ мерча", "order_merch"));

        // Вторая строка — Благотворительность и назад
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(createButton("❤️ Благотворительность", "order_charity"));
        row2.add(createButton("🔙 Назад", "back_to_main"));

        rows.add(row1);
        rows.add(row2);

        markup.setKeyboard(rows);

        telegramBot.sendMessageWithMarkup(chatId, "🛒 Выберите тип заказа:", markup);
    }

    // 🍽 Заказ стола
    private void handleTableOrder(Long chatId) {
        telegramBot.sendTextMessage(chatId, "Пожалуйста, выберите стол для заказа.");

        // Пример: Создание объекта брони и сохранение в базу
        TableReservationOrder order = new TableReservationOrder();
        order.setStatus("PENDING");
        orderService.createTableReservation(order);

        telegramBot.sendTextMessage(chatId, "✅ Заказ стола создан! Ожидайте подтверждения.");
    }

    // 🛍 Заказ мерча
    private void handleMerchOrder(Long chatId) {
        telegramBot.sendTextMessage(chatId, "Выберите товар для заказа.");

        // Пример: Создание объекта заказа мерча и сохранение в базу
        MerchOrder order = new MerchOrder();
        order.setItemName("Футболка Astor");
        order.setQuantity(1);
        order.setStatus("PENDING");
        orderService.createMerchOrder(order);

        telegramBot.sendTextMessage(chatId, "✅ Заказ мерча создан! Ожидайте подтверждения.");
    }

    // ❤️ Благотворительность
    private void handleCharityOrder(Long chatId) {
        telegramBot.sendTextMessage(chatId, "Укажите сумму пожертвования.");

        // Пример: Создание объекта пожертвования и сохранение в базу
        CharityOrder order = new CharityOrder();
        order.setDonationAmount(new BigDecimal("100.00")); // В реальной логике значение должно приходить из user input
        order.setMessage("Благотворительность в поддержку Astor");
        order.setStatus("PENDING");
        orderService.createCharityOrder(order);

        telegramBot.sendTextMessage(chatId, "❤️ Спасибо за пожертвование! Оно будет использовано на благие цели.");
    }

    // 🔙 Назад в главное меню
    private void handleBackToMainMenu(Long chatId) {
        telegramBot.sendTextMessage(chatId, "🔙 Возврат в главное меню...");
    }

    // Вспомогательный метод для создания кнопки
    private InlineKeyboardButton createButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }
}