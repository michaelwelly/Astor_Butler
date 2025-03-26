package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.button.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@TelegramCommand("/main_menu")
@RequiredArgsConstructor
@Component
public class MainMenuCommand implements BotCommand {

    private final MenuButton menuButton;
    private final TableButton tableButton;
    private final SlotButton slotButton;
    private final FeedbackButton feedbackButton;
    private final OrderButton orderButton;
    private final BalanceButton balanceButton;
    private final RazjebButton razjebButton;
    private final AfishaButton afishaButton;

    @Override
    public String getCommand() {
        return "/main_menu";
    }

    @Override
    public String execute(Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = List.of(
                menuButton.createMenuButton().getKeyboard().get(0).get(0),
                tableButton.createTableButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row2 = List.of(
                balanceButton.createBalanceButton().getKeyboard().get(0).get(0),
                slotButton.createSlotButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row3 = List.of(
                orderButton.createOrderButton().getKeyboard().get(0).get(0),
                feedbackButton.createFeedbackButton().getKeyboard().get(0).get(0),
                razjebButton.createRazjebButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row4 = List.of(
                afishaButton.createAfishaButton().getKeyboard().get(0).get(0)
        );

        markup.setKeyboard(List.of(row1, row2, row3, row4));

        return "Главное меню 👇";
    }

}

