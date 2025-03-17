package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.User;
import museon_online.astor_butler.telegram.BotState;
import museon_online.astor_butler.telegram.TelegramBot;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import museon_online.astor_butler.utils.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramOAuthService {

    private final UserService userService;
    private final TelegramExceptionHandler exceptionHandler;
    private final TelegramBot telegramBot;

    public void handlePhoneInput(Update update) {
        Long chatId = TelegramUtils.getChatIdFromUpdate(update);
        String phoneNumber = update.getMessage().getText();

        if (isValidPhoneNumber(phoneNumber)) {
            User user = userService.findByTelegramId(update.getMessage().getFrom().getId().toString());
            user.setPhoneNumber(phoneNumber);
            userService.save(user);

            telegramBot.sendTextMessage(chatId, "✅ Номер телефона успешно сохранён!");
            telegramBot.getUserState().put(chatId, BotState.READY);
        } else {
            telegramBot.sendTextMessage(chatId, "❌ Неверный формат номера. Попробуйте ещё раз.");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{7,15}$");
    }
}
