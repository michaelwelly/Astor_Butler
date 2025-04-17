package museon_online.astor_butler.telegram.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import museon_online.astor_butler.telegram.TelegramRouter;
import museon_online.astor_butler.telegram.command.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;
import museon_online.astor_butler.telegram.context.CommandContextBuilder;
import museon_online.astor_butler.telegram.state.BotState;
import museon_online.astor_butler.telegram.command.CommandRegistry;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import museon_online.astor_butler.user.service.UserGateService;
import museon_online.astor_butler.user.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class TelegramBot extends TelegramLongPollingBot {

    private final UserGateService userGateService;
    private final CommandContextBuilder contextBuilder;
    private final TelegramRouter telegramRouter;
    private final TelegramExceptionHandler exceptionHandler;
    private final Map<Long, BotState> userState = new HashMap<>();
    private final TelegramBotProperties botProperties;

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
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = TelegramUtils.getChatIdFromUpdate(update);

        if (chatId != null && userState.get(chatId) == BotState.WAITING_FOR_PHONE) {
            return;
        }

        if (!userGateService.isAuthorized(update)) {
            sendMessage(chatId, "⛔ Вы не прошли авторизацию через Telegram.");
            return;
        }

        userGateService.loadOrCreateUser(update);

        try {
            AstorUpdate astorUpdate = new AstorUpdate(update);
            CommandContext context = contextBuilder.build(astorUpdate);
            BotResponse response = telegramRouter.route(update);

            if (response != null) {
                if (response.hasMarkup()) {
                    sendMessageWithMarkup(chatId, response.getText(), response.getMarkup());
                } else {
                    sendMessage(chatId, response.getText());
                }
            }
        } catch (Exception e) {
            log.error("Ошибка в обработке команды: {}", e.getMessage(), e);
            if (chatId != null) {
                exceptionHandler.handleException(new TelegramApiException("Ошибка в обработке команды", e), this, chatId);
            }
        }
    }

    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        log.debug("Получена callback-команда: {}", callbackData);

        BotResponse response = commandRegistry.executeCommand(callbackData, update);
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        if (response.hasMarkup()) {
            sendMessageWithMarkup(chatId, response.getText(), response.getMarkup());
        } else {
            sendMessage(chatId, response.getText());
        }
    }

    private void handleCommand(Update update) {
        String command = update.getMessage().getText().split(" ")[0];
        log.debug("Получена текстовая команда: {}", command);

        BotResponse response = commandRegistry.executeCommand(command, update);
        Long chatId = update.getMessage().getChatId();

        if (response.hasMarkup()) {
            sendMessageWithMarkup(chatId, response.getText(), response.getMarkup());
        } else {
            sendMessage(chatId, response.getText());
        }
    }

    public void sendMessage(Long chatId, String message) {
        if (message == null || message.isBlank()) {
            log.debug("Пустое сообщение — игнорируем отправку");
            return;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
            log.debug("Сообщение отправлено в чат {}: {}", chatId, message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения в чат {}: {}", chatId, message, e);
            exceptionHandler.handleException(e,this, chatId);
        }
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

    public void sendMessageWithMarkup(Long chatId, String message, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(markup);

        try {
            execute(sendMessage);
            log.debug("Сообщение с разметкой отправлено в чат {}: {}", chatId, message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения с разметкой в чат {}: {}", chatId, message, e);
            exceptionHandler.handleException(e, this, chatId);
        }
    }
}
