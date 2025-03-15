package museon_online.astor_butler.repository;

import museon_online.astor_butler.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByTelegramIdTest() {
        User user = new User(null, "12345", "John", "Doe", "johndoe", "1234567890");
        userRepository.save(user);

        Optional<User> result = userRepository.findByTelegramId("12345");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void userNotFoundTest() {
        Optional<User> result = userRepository.findByTelegramId("not_found");
        assertTrue(result.isEmpty());
    }
}
