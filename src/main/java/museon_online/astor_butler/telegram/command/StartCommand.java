package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@RequiredArgsConstructor
@TelegramCommand("/start")
@Component
public class StartCommand implements BotCommand {

    private final MainMenuCommand mainMenuCommand;
    private final TelegramExceptionHandler exceptionHandler;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String execute(Update update) {
        try {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            InlineKeyboardButton loginButton = new InlineKeyboardButton("Войти через Telegram");
            loginButton.setUrl("https://oauth.telegram.org/auth?bot_id=7663104943&redirect_uri=https://your-app.com/login/oauth2/code/telegram");

            markup.setKeyboard(List.of(List.of(loginButton)));

            return "Привет! Для продолжения авторизуйся через Telegram 👇";
        } catch (Exception e) {
            exceptionHandler.handleException(new TelegramApiException("Ошибка при выполнении команды /start", e));
            return "Что-то пошло не так... Отправляю тебя на Луну! 🚀🌕";
        }
    }

    public String onAuthorizationComplete(Update update) {
        try {
            return mainMenuCommand.execute(update);
        } catch (Exception e) {
            exceptionHandler.handleException(new TelegramApiException("Ошибка при открытии меню", e));
            return "Не удалось открыть меню — попробуй снова! 🚀";
        }
    }
}

