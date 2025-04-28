package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.utils.TelegramBot;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import museon_online.astor_butler.telegram.utils.TelegramUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static museon_online.astor_butler.telegram.utils.TelegramUtils.getChatIdFromUpdate;

@Slf4j
@Component
@TelegramCommand("/start")
@RequiredArgsConstructor
public class StartCommand implements BotCommand {

    private final TelegramExceptionHandler exceptionHandler;
    private final TelegramBot telegramBot;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getDescription() {
        return "Запуск бота и приветствие пользователя.";
    }

    @Override
    public BotResponse execute(Update update) {
        try {
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

            InlineKeyboardButton agreeButton = new InlineKeyboardButton("\u2705 Согласен и продолжаем");
            agreeButton.setCallbackData("policy:agree");

            InlineKeyboardButton declineButton = new InlineKeyboardButton("\uD83D\uDEAA Не согласен");
            declineButton.setCallbackData("policy:decline");

            markup.setKeyboard(List.of(List.of(agreeButton), List.of(declineButton)));

            String text = "\uD83D\uDD10 Уважаемый гость,\n\n"
                    + "Перед тем как открыть Вам доступ к эфиру и продолжить наше знакомство, прошу подтвердить своё согласие на обработку персональных данных.\n\n"
                    + "📄 Ознакомиться с политикой: https://docs.google.com/document/d/1RxoK6MYSmOR4nL_0MIWhvtbuNLOdWEXuKMhP1lhKnTw\n\n"
                    + "Прошу выбрать, как мы с Вами поступим:";

            return new BotResponse(text, markup);

        } catch (Exception e) {
            log.error("Ошибка при выполнении команды /start: {}", e.getMessage(), e);
            Long chatId = getChatIdFromUpdate(update);
            if (chatId != null) {
                exceptionHandler.handleException(
                        new TelegramApiException("Ошибка при выполнении команды /start", e),
                        telegramBot,
                        chatId
                );
            }
            return new BotResponse("Произошла ошибка при запуске. Попробуйте позже.");
        }
    }
}
