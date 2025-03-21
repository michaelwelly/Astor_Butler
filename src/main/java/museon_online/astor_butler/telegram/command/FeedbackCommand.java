package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@TelegramCommand("/feedback")
@RequiredArgsConstructor
public class FeedbackCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "/feedback";
    }

    @Override
    public String execute(Update update) {
        return "Оставьте ваш отзыв, мы обязательно его прочитаем! 🙌";
    }
}
