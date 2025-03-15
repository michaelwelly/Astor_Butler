package museon_online.astor_butler.telegram;

import museon_online.astor_butler.telegram.exception.TelegramExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandRegistry commandRegistry;
    private final String botToken;
    private final TelegramExceptionHandler exceptionHandler;

    @Autowired
    public TelegramBot(CommandRegistry commandRegistry, TelegramExceptionHandler exceptionHandler,
                       @Value("${telegram.bot.token}") String botToken) {
        this.commandRegistry = commandRegistry;
        this.exceptionHandler = exceptionHandler;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return "YourBotUsername"; // Имя бота
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String response = commandRegistry.executeCommand(callbackData, update);
            sendMessage(update.getCallbackQuery().getMessage().getChatId(), response);
        } else if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText().split(" ")[0];
            String response = commandRegistry.executeCommand(command, update);
            sendMessage(update.getMessage().getChatId(), response);
        }
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            exceptionHandler.handleException(e);
        }
    }
}

