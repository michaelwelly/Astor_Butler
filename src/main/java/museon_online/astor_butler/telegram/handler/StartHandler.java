package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.utils.BotResponse;
import museon_online.astor_butler.telegram.command.StartCommand;
import museon_online.astor_butler.telegram.command.MainMenuCommand;
import museon_online.astor_butler.telegram.utils.AstorUpdate;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartHandler {

    private final MainMenuCommand mainMenuCommand;
    private final StartCommand startCommand;
    private final TelegramExceptionHandler exceptionHandler;

    public BotResponse handle(Update update) {
        AstorUpdate astor = new AstorUpdate(update);
        try {
            if (astor.text() != null && "⬅️ Назад".equals(astor.text().trim())) {
                log.info("Пользователь нажал 'Назад', перенаправляем в StartCommand");
                return startCommand.execute(update);
            }

            String data = astor.raw().getCallbackQuery().getData();
            Long chatId = astor.chatId();

            return switch (data) {
                case "policy:agree" -> buildRequestPhoneNumberResponse();
                case "policy:decline" -> new BotResponse("Понимаю. Ваши данные не будут сохранены.\n\nВы можете вернуться в любое время, отправив /start.");
                default -> {
                    log.warn("Неизвестный callback data в StartHandler: {}", data);
                    yield new BotResponse("Что-то пошло не так. Попробуйте снова.");
                }
            };
        } catch (Exception e) {
            log.error("Ошибка в StartHandler", e);
            return new BotResponse("Произошла ошибка при обработке. Попробуйте позже.");
        }
    }

    private BotResponse buildRequestPhoneNumberResponse() {
        KeyboardButton contactButton = new KeyboardButton("📱 Отправить номер телефона");
        contactButton.setRequestContact(true);

        KeyboardButton backButton = new KeyboardButton("⬅️ Назад");

        KeyboardRow row1 = new KeyboardRow();
        row1.add(contactButton);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(backButton);

        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(List.of(row1, row2));
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);

        return BotResponse.withReplyKeyboard(
                "Пожалуйста, подтвердите номер телефона, чтобы мы могли продолжить работу с Вами.\n\nИли нажмите \"⬅️ Назад\", чтобы вернуться к выбору.",
                replyMarkup
        );
    }
}