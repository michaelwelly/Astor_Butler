package museon_online.astor_butler.user.service;

import museon_online.astor_butler.user.model.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserGateService {
    boolean isAuthorized(Update update);
    User loadOrCreateUser(Update update);
}
