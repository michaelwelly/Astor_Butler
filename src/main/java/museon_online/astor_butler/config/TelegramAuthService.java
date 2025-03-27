package museon_online.astor_butler.auth;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.command.MainMenuCommand;
import museon_online.astor_butler.telegram.state.BotState;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramAuthService {

    private final UserService userService;
    private final TelegramBot telegramBot;
    private final MainMenuCommand mainMenuCommand;

    public void authorizeOrContinue(Update update) {
        Message message = update.getMessage();
        String telegramId = message.getFrom().getId().toString();
        Long chatId = message.getChatId();

        userService.saveUser(message.getFrom());

        User user = userService.findUserByTelegramId(telegramId);

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isBlank()) {
            telegramBot.sendRequestPhoneMessage(chatId);
            telegramBot.getUserState().put(chatId, BotState.AWAITING_PHONE);
        } else {
            telegramBot.sendTextMessage(chatId, "👋 С возвращением, " + user.getFirstName() + "!");
            mainMenuCommand.execute(update);
            telegramBot.getUserState().put(chatId, BotState.READY);
        }
    }

    public void handlePhone(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String telegramId = message.getFrom().getId().toString();
        String phone = message.getText();

        if (isValidPhoneNumber(phone)) {
            userService.updatePhoneNumber(telegramId, phone);
            telegramBot.sendTextMessage(chatId, "✅ Номер телефона сохранён.");
            mainMenuCommand.execute(update);
            telegramBot.getUserState().put(chatId, BotState.READY);
        } else {
            telegramBot.sendRequestPhoneMessage(chatId);
        }
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^\\+?[0-9]{7,15}$");
    }
}