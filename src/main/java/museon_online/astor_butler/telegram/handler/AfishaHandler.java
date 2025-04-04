package museon_online.astor_butler.telegram.handler;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.poster.EventPosterDto;
import museon_online.astor_butler.poster.PosterParserService;
import museon_online.astor_butler.poster.PosterService;
import museon_online.astor_butler.telegram.utils.AstorUpdate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AfishaHandler implements AstorBotHandler {

    private final PosterParserService posterParserService;
    private final PosterService posterService;

    @Override
    public boolean appliesTo(AstorUpdate update) {
        return update.text() != null && posterParserService.isValid(update.text());
    }

    @Override
    public void handle(AstorUpdate update) {
        String rawText = update.text();

        try {
            EventPosterDto dto = posterParserService.parse(rawText);
            UUID posterId = posterService.save(dto);
            update.reply("🎭 Афиша успешно добавлена в Butler Repertoire \nID: " + posterId);
        } catch (Exception e) {
            update.reply("⚠️ Ошибка при обработке афиши: " + e.getMessage());
        }
    }

    public String handleAfisha() {
        return posterService.getThisWeekPostersFormatted();
    }
}
