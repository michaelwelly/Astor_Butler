package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.Role;
import museon_online.astor_butler.model.User;
import museon_online.astor_butler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Value("${app.owner.telegram-id}")
    private String ownerTelegramId;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByTelegramId(String telegramId) {
        return userRepository.findByTelegramId(telegramId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User assignRole(UUID userId, Role role) {
        User user = getUserById(userId);

        if (!user.getTelegramId().equals(ownerTelegramId)) {
            throw new SecurityException("Вы не можете менять роли пользователей");
        }

        if (role == Role.ROLE_ADMIN) {
            throw new UnsupportedOperationException("Роль ROLE_ADMIN недоступна для изменения");
        }

        user.setRole(role);
        return userRepository.save(user);
    }

    public User assignManagerRole(UUID userId) {
        User user = getUserById(userId);

        if (!user.getTelegramId().equals(ownerTelegramId)) {
            throw new SecurityException("Вы не можете назначить роль менеджера");
        }

        user.setRole(Role.ROLE_MANAGER);
        return userRepository.save(user);
    }

    public User createUser(User user) {
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new RuntimeException("Phone number is required");
        }

        user.setRole(Role.ROLE_GUEST);
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UUID id, User updatedUser) {
        User user = getUserById(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setUsername(updatedUser.getUsername());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
