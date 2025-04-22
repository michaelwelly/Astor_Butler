package museon_online.astor_butler.telegram.command;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.telegram.TelegramCommand;
import museon_online.astor_butler.user.service.UserService;
import museon_online.astor_butler.telegram.command.BotResponse;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Contact;

@TelegramCommand
@RequiredArgsConstructor
public class ContactHandlerCommand implements BotCommand {

    private final UserService userService;

    @Override
    public String getCommand() {
        return "CONTACT_HANDLER"; // не вызывается напрямую, регистрируется вручную
    }

    @Override
    public String getDescription() {
        return "Обработка номера телефона для авторизации.";
    }

    @Override
    public BotResponse execute(Update update) {
        Contact contact = update.getMessage().getContact();
        userService.upsertFromContact(contact);

        return new BotResponse("""
            ✅ Спасибо, ты верифицирован!
            Добро пожаловать в Astor Butler 👔
            Жми /menu чтобы открыть главное меню.
        """);
    }
}