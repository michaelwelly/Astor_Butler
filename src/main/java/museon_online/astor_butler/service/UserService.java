package museon_online.astor_butler.service;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.model.Role;
import museon_online.astor_butler.model.User;
import museon_online.astor_butler.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User assignRole(UUID userId, Role role) {
        if (role == Role.ROLE_ADMIN) {
            throw new UnsupportedOperationException("Cannot assign ROLE_ADMIN through API");
        }
        User user = getUserById(userId);
        user.setRole(role);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User assignManagerRole(UUID userId) {
        User user = getUserById(userId);
        user.setRole(Role.ROLE_MANAGER);
        return userRepository.save(user);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User createUser(User user) {
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            throw new RuntimeException("Phone number is required");
        }

        user.setRole(Role.ROLE_GUEST);
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
