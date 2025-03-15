package museon_online.astor_butler.telegram.command;

import museon_online.astor_butler.telegram.button.SlotButton;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.stereotype.Component;

@Component
public class SlotCommand implements BotCommand {

    private final SlotButton slotButton;

    public SlotCommand(SlotButton slotButton) {
        this.slotButton = slotButton;
    }

    @Override
    public String execute(Update update) {
        return "🕒 Переход к выбору слотов...";
    }

    @Override
    public String getCommand() {
        return "/slots";
    }

}
