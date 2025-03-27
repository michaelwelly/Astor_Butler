package museon_online.astor_butler.user.controller;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.user.model.User;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam String telegramId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String languageCode) {
        try {
            userService.registerUser(firstName, lastName, telegramId, username, languageCode);
            return ResponseEntity.ok("Пользователь успешно зарегистрирован!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{telegramId}/phone")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable String telegramId,
            @RequestParam String phoneNumber) {
        try {
            userService.updatePhoneNumber(telegramId, phoneNumber);
            return ResponseEntity.ok("Номер телефона обновлён!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save-from-telegram")
    public ResponseEntity<String> saveUserFromTelegram(@RequestBody org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        try {
            userService.saveUser(telegramUser);
            return ResponseEntity.ok("Пользователь сохранён из Telegram!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<User> findUserByTelegramId(@PathVariable String telegramId) {
        User user = userService.findUserByTelegramId(telegramId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
