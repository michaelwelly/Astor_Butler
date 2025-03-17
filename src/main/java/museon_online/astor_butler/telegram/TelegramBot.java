package museon_online.astor_butler.telegram;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandRegistry commandRegistry;
    private final TelegramExceptionHandler exceptionHandler;
    private final String botToken;

    @Override
    public String getBotUsername() {
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                handleCallback(update);
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                handleCommand(update);
            }
        } catch (Exception e) {
            log.error("Ошибка в обработке команды: {}", e.getMessage());
            exceptionHandler.handleException(new TelegramApiException("Ошибка в обработке команды", e));
        }
    }

    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        String response = commandRegistry.executeCommand(callbackData, update);
        sendMessage(update.getCallbackQuery().getMessage().getChatId(), response);
    }

    private void handleCommand(Update update) {
        String command = update.getMessage().getText().split(" ")[0];
        String response = commandRegistry.executeCommand(command, update);
        sendMessage(update.getMessage().getChatId(), response);
    }

    private void sendMessage(Long chatId, String message) {
        if (message == null || message.isBlank()) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения: {}", e.getMessage());
            exceptionHandler.handleException(e);
        }
    }
}
