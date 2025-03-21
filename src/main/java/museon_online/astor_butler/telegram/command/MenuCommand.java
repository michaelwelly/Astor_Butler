package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@TelegramCommand("/menu")
@RequiredArgsConstructor
public class MenuCommand implements BotCommand {

    private static final Map<String, String> menuFiles = new HashMap<>();

    static {
        menuFiles.put("MENU AERIS", "/home/michael/aeris_workspace/MENU AERIS.pdf");
        menuFiles.put("AERIS WINE ROOM", "/home/michael/aeris_workspace/AERIS WINE ROOM.pdf");
        menuFiles.put("AERIS 10 MENU", "/home/michael/aeris_workspace/AERIS 10 MENU.pdf");
        menuFiles.put("AERIS DAILY MENU", "/home/michael/aeris_workspace/AERIS DAILY MENU.pdf");
        menuFiles.put("BAR AERIS", "/home/michael/aeris_workspace/BAR AERIS.pdf");
        menuFiles.put("ELEMENTS CARD", "/home/michael/aeris_workspace/ELEMENTS CARD.pdf");
    }

    @Override
    public String getCommand() {
        return "menu_file";
    }

    @Override
    public String execute(Update update) {
        String command = update.getMessage().getText();
        String filePath = menuFiles.get(command);

        if (filePath != null) {
            return sendPdf(update.getMessage().getChatId(), filePath);
        }
        return "Файл не найден.";
    }

    private String sendPdf(Long chatId, String filePath) {
        SendDocument document = new SendDocument();
        document.setChatId(chatId.toString());
        document.setDocument(new org.telegram.telegrambots.meta.api.objects.InputFile(new java.io.File(filePath)));
        document.setCaption("📄 Ваш запрошенный файл.");
        return "Файл отправлен!";
    }
}
