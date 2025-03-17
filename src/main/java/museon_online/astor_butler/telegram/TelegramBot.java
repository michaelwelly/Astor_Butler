package museon_online.astor_butler.telegram;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.service.TelegramOAuthService;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import museon_online.astor_butler.utils.TelegramUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@SuppressWarnings("deprecation")
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandRegistry commandRegistry;
    private final TelegramExceptionHandler exceptionHandler;
    private final String botToken;
    private final Map<Long, BotState> userState = new HashMap<>();

    @Autowired
    private TelegramOAuthService telegramOAuthService;

    public TelegramBot(CommandRegistry commandRegistry,
                       TelegramExceptionHandler exceptionHandler,
                       @Value("${telegram.bot.token}") String botToken) {
        this.commandRegistry = commandRegistry;
        this.exceptionHandler = exceptionHandler;
        this.botToken = botToken;
    }

    @PostConstruct
    public void init() {
        exceptionHandler.setBot(this);
    }

    @Override
    public String getBotUsername() {
        return "aeris_dvorestsky_bot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = TelegramUtils.getChatIdFromUpdate(update);

        if (chatId != null && userState.get(chatId) == BotState.WAITING_FOR_PHONE) {
            telegramOAuthService.handlePhoneInput(update);
            return;
        }

        try {
            if (update.hasCallbackQuery()) {
                handleCallback(update);
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                handleCommand(update);
            } else {
                log.debug("Игнорируемое обновление: {}", update);
            }
        } catch (Exception e) {
            log.error("Ошибка в обработке команды: {}", e.getMessage(), e);
            if (chatId != null) {
                exceptionHandler.handleException(new TelegramApiException("Ошибка в обработке команды", e), this, chatId);
            } else {
                log.warn("Не удалось определить chatId для отправки сообщения об ошибке.");
            }
        }
    }

    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        log.debug("Получена callback-команда: {}", callbackData);

        String response = commandRegistry.executeCommand(callbackData, update);
        sendMessage(update.getCallbackQuery().getMessage().getChatId(), response);
    }

    private void handleCommand(Update update) {
        String command = update.getMessage().getText().split(" ")[0];
        log.debug("Получена текстовая команда: {}", command);

        String response = commandRegistry.executeCommand(command, update);
        sendMessage(update.getMessage().getChatId(), response);
    }

    private void sendMessage(Long chatId, String message) {
        if (message == null || message.isBlank()) {
            log.debug("Пустое сообщение — игнорируем отправку");
            return;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
            log.debug("Сообщение отправлено в чат {}: {}", chatId, message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения: {}", e.getMessage());
            exceptionHandler.handleException(e,this, chatId);
        }
    }

    public void sendRequestPhoneMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Пожалуйста, введите ваш номер телефона в формате +1234567890:");

        try {
            execute(message);
            userState.put(chatId, BotState.WAITING_FOR_PHONE);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке запроса на номер: {}", e.getMessage(), e);
        }
    }

    public Map<Long, BotState> getUserState() {
        return userState;
    }

    public void sendTextMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения: {}", e.getMessage(), e);
        }
    }
}
