package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.button.*;
import museon_online.astor_butler.telegram.utils.BotCommand;
import museon_online.astor_butler.telegram.utils.BotResponse;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class MainMenuCommand implements BotCommand {

    private final MenuButton menuButton;
    private final TableReservationButton tableReservationButton;
    private final SlotButton slotButton;
    private final BalanceButton balanceButton;
    private final MerchButton merchButton;
    private final FeedbackButton feedbackButton;
    private final RazjebButton razjebButton;
    private final ContactButton contactButton;
    private final CancelButton cancelButton;
    private final TipButton tipButton;
    private final CharityButton charityButton;
    private final PosterButton posterButton;

    @Override
    public String getCommand() {
        return "/main_menu";
    }

    @Override
    public String getDescription() {
        return "Показать главное меню";
    }

    @Override
    public BotResponse execute(Update update) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> row1 = List.of(
                menuButton.buildButton().getKeyboard().get(0).get(0),
                balanceButton.buildButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row2 = List.of(
                slotButton.buildButton().getKeyboard().get(0).get(0),
                tableReservationButton.buildButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row3 = List.of(
                merchButton.buildButton().getKeyboard().get(0).get(0),
                razjebButton.buildButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row4 = List.of(
                posterButton.buildButton().getKeyboard().get(0).get(0),
                feedbackButton.buildButton().getKeyboard().get(0).get(0)

        );

        List<InlineKeyboardButton> row5 = List.of(
                tipButton.buildButton().getKeyboard().get(0).get(0),
                charityButton.buildButton().getKeyboard().get(0).get(0)
        );

        List<InlineKeyboardButton> row6 = List.of(
                contactButton.buildButton().getKeyboard().get(0).get(0),
                cancelButton.buildButton().getKeyboard().get(0).get(0)
        );

        markup.setKeyboard(List.of(row1, row2, row3, row4, row5, row6));

        return new BotResponse("Главное меню 👇", markup);
    }
}