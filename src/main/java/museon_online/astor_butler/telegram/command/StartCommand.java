package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static museon_online.astor_butler.telegram.utils.TelegramUtils.getChatIdFromUpdate;

@Slf4j
@TelegramCommand("/start")
@RequiredArgsConstructor
public class StartCommand implements BotCommand {

    private final TelegramExceptionHandler exceptionHandler;
    private final TelegramBot telegramBot;
    private final MainMenuCommand mainMenuCommand;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "Команда для начала работы с ботом.";
    }

    @Override
    public void execute(Update update) {
        try {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            InlineKeyboardButton loginButton = new InlineKeyboardButton("Войти через Telegram");
            loginButton.setUrl("https://oauth.telegram.org/auth?bot_id=7663104943&redirect_uri=https://your-app.com/login/oauth2/code/telegram");

            markup.setKeyboard(List.of(List.of(loginButton)));

            Long chatId = getChatIdFromUpdate(update);
            telegramBot.sendMessageWithMarkup(chatId,
                    "Привет! Для продолжения авторизуйся через Telegram 👇\n" +
                    "Если не хочешь авторизовываться — отправляюсь на Луну! 🚀🌕",
                    markup);

            mainMenuCommand.execute(update);
        } catch (Exception e) {
            log.error("Ошибка при выполнении команды /start: {}", e.getMessage(), e);
            Long chatId = getChatIdFromUpdate(update);
            if (chatId != null) {
                exceptionHandler.handleException(
                        new TelegramApiException("Ошибка при выполнении команды /start", e),
                        telegramBot,
                        chatId
                );
            } else {
                log.warn("Не удалось определить chatId для отправки сообщения об ошибке.");
            }
        }
    }
}
