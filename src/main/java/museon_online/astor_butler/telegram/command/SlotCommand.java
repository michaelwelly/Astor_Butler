package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.button.SlotButton;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@TelegramCommand("/slots")
@RequiredArgsConstructor
public class SlotCommand implements BotCommand {

    private final SlotButton slotButton;

    @Override
    public String execute(Update update) {
        return "🕒 Переход к выбору слотов...";
    }

    @Override
    public String getCommand() {
        return "/slots";
    }

}
