package museon_online.astor_butler.user.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.user.model.UserStatus;
import museon_online.astor_butler.user.repository.RoleRepository;
import museon_online.astor_butler.user.repository.UserRepository;
import museon_online.astor_butler.user.model.Role;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.repository.UserStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserStatusRepository userStatusRepository;

    public void registerUser(String firstName, String lastName, String telegramId, String username, String languageCode) {
        if (userRepository.findByTelegramId(telegramId).isPresent()) {
            throw new RuntimeException("Пользователь с таким Telegram ID уже существует");
        }

        Role defaultRole = roleRepository.findById("ROLE_GUEST")
                .orElseThrow(() -> new IllegalStateException("Роль ROLE_GUEST не найдена"));

        UserStatus defaultStatus = userStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new IllegalStateException("Статус ACTIVE не найден"));

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTelegramId(telegramId);
        user.setUsername(username);
        user.setLanguageCode(languageCode);
        user.setRole(defaultRole);
        user.setStatus(defaultStatus);

        userRepository.save(user);
    }

    public void updatePhoneNumber(String telegramId, String phoneNumber) {
        User user = userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }

    public void saveUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        Optional<User> optionalUser = userRepository.findByTelegramId(telegramUser.getId().toString());
        if (optionalUser.isPresent()) return;

        Role defaultRole = roleRepository.findById("ROLE_GUEST")
                .orElseThrow(() -> new IllegalStateException("Роль ROLE_GUEST не найдена"));

        UserStatus defaultStatus = userStatusRepository.findById("ACTIVE")
                .orElseThrow(() -> new IllegalStateException("Статус ACTIVE не найден"));

        User user = new User();
        user.setTelegramId(String.valueOf(telegramUser.getId()));
        user.setFirstName(telegramUser.getFirstName());
        user.setLastName(telegramUser.getLastName());
        user.setUsername(telegramUser.getUserName());
        user.setLanguageCode(telegramUser.getLanguageCode());
        user.setRole(defaultRole);
        user.setStatus(defaultStatus);

        userRepository.save(user);
    }

    public User findUserByTelegramId(String telegramId) {
        return userRepository.findByTelegramId(telegramId).orElse(null);
    }
}
