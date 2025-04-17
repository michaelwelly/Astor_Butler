package museon_online.astor_butler.user.service;

import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.TelegramAuthService;
import museon_online.astor_butler.telegram.utils.TelegramUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserGateServiceImpl implements UserGateService {

    private final TelegramAuthService telegramAuthService;
    private final UserService userService;
    private final String botToken;

    public UserGateServiceImpl(TelegramAuthService telegramAuthService,
                               UserService userService,
                               @Value("${telegram.bot.token}") String botToken) {
        this.telegramAuthService = telegramAuthService;
        this.userService = userService;
        this.botToken = botToken;
    }

    @Override
    public boolean isAuthorized(Update update) {
        String telegramId = update.getMessage().getFrom().getId().toString();
        String hash = TelegramUtils.extractHash(update);
        return telegramAuthService.isValidTelegramUser(telegramId, hash, botToken);
    }

    @Override
    public User loadOrCreateUser(Update update) {
        userService.saveUser(update.getMessage().getFrom());
        return userService.findUserByTelegramId(update.getMessage().getFrom().getId().toString());
    }
}