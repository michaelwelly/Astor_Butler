package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.reference.MenuCategory;
import museon_online.astor_butler.reference.MenuPdf;
import museon_online.astor_butler.reference.MenuPdfService;
import museon_online.astor_butler.telegram.command.BotCommand;
import museon_online.astor_butler.telegram.command.BotResponse;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuHandler implements BotCommand {

    private final MenuPdfService menuPdfService;

    @Override
    public String getCommand() {
        return "menu_handler"; // заглушка, напрямую не вызывается
    }

    @Override
    public String getDescription() {
        return "Обработчик PDF-меню по категориям";
    }

    @Override
    public BotResponse execute(Update update) {
        if (!update.hasCallbackQuery()) {
            return new BotResponse("⛔ Некорректный запрос.");
        }

        String callbackData = update.getCallbackQuery().getData();
        if (!callbackData.startsWith("menu:")) {
            return new BotResponse("😕 Неизвестная категория меню.");
        }

        MenuCategory category = resolveCategory(callbackData);
        List<MenuPdf> pdfList = menuPdfService.getPdfMenu(category);

        if (pdfList.isEmpty()) {
            return new BotResponse("📭 В этом разделе пока нет меню.");
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (MenuPdf pdf : pdfList) {
            keyboard.add(List.of(
                    InlineKeyboardButton.builder()
                            .text(pdf.getTitle())
                            .url(pdf.getFileUrl())  // локальная ссылка типа https://your-bot.com/menu/pdf/{id}
                            .build()
            ));
        }

        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(keyboard).build();
        return new BotResponse("📄 Выберите PDF:", markup);
    }

    private MenuCategory resolveCategory(String callbackData) {
        try {
            return MenuCategory.valueOf(callbackData.replace("menu:", "").toUpperCase());
        } catch (IllegalArgumentException e) {
            return MenuCategory.AERIS_MENU; // fallback
        }
    }
}