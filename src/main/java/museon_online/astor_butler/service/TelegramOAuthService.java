package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.User;
import museon_online.astor_butler.telegram.TelegramBot;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramOAuthService {

    private final UserService userService;
    private final TelegramExceptionHandler exceptionHandler;
    private final TelegramBot telegramBot;

    public void processOAuthResponse(Update update) {
        String telegramId = update.getMessage().getFrom().getId().toString();

        User user = userService.getUserByTelegramId(telegramId);

        if (user == null) {
            user = new User();
            user.setTelegramId(telegramId);
            user.setFirstName(update.getMessage().getFrom().getFirstName());
            user.setLastName(update.getMessage().getFrom().getLastName());
            user.setUsername(update.getMessage().getFrom().getUserName());

            user.setRequiresPhone(true);
            userService.createUser(user);
        }

        if (user.isRequiresPhone()) {
            telegramBot.sendRequestPhoneMessage(update.getMessage().getChatId());
        }
    }
}
