package museon_online.astor_butler.config;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.command.MainMenuCommand;
import museon_online.astor_butler.telegram.state.BotState;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.user.service.UserService;
import museon_online.astor_butler.telegram.utils.TelegramUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramOAuthService {

    private final UserService userService;
    private final TelegramBot telegramBot;
    private final MainMenuCommand mainMenuCommand; // добавлено

    public void handlePhoneInput(Update update) {
        Long chatId = TelegramUtils.getChatIdFromUpdate(update);
        String phoneNumber = update.getMessage().getText();

        if (isValidPhoneNumber(phoneNumber)) {
            String telegramId = update.getMessage().getFrom().getId().toString();
            userService.updatePhoneNumber(telegramId, phoneNumber);

            telegramBot.sendTextMessage(chatId, "✅ Номер телефона успешно сохранён!");
            mainMenuCommand.execute(update); // добавлено
            telegramBot.getUserState().put(chatId, BotState.READY);
        } else {
            telegramBot.sendRequestPhoneMessage(chatId);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{7,15}$");
    }
}
