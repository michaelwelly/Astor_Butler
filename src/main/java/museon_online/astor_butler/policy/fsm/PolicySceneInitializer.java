package museon_online.astor_butler.policy.fsm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import museon_online.astor_butler.fsm.api.SceneInitializer;
import museon_online.astor_butler.fsm.api.SceneRegistry;
import museon_online.astor_butler.fsm.api.StateHandler;
import museon_online.astor_butler.fsm.api.TransitionContext;
import museon_online.astor_butler.telegram.util.TelegramUtils;
import museon_online.astor_butler.telegram.TelegramSender;
import museon_online.astor_butler.telegram.ButtonBuilder;
import policy.fsm.PolicyAgreementState;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicySceneInitializer implements SceneInitializer {

    private final TelegramSender telegramSender;

    @Override
    public String sceneId() {
        return "policy-agreement";
    }

    @Override
    public void register(SceneRegistry registry) {
        registry
                .onEnter(PolicyAgreementState.AWAITING_AGREEMENT, sendAgreementRequest());
    }

    private StateHandler sendAgreementRequest() {
        return (TransitionContext ctx) -> {
            Long chatId = ctx.getHeader().getChatId();
            String text = "\uD83D\uDD10 Уважаемый гость,\n\n" +
                    "Перед тем как открыть Вам доступ к эфиру и продолжить наше знакомство, прошу подтвердить своё согласие на обработку персональных данных.\n\n" +
                    "📄 Ознакомиться с политикой: https://docs.google.com/document/d/1RxoK6MYSmOR4nL_0MIWhvtbuNLOdWEXuKMhP1lhKnTw\n\n" +
                    "Прошу выбрать, как мы с Вами поступим:";

            var keyboard = ButtonBuilder.inline()
                    .button("\u2705 Согласен и продолжаем", "policy:agree")
                    .button("\uD83D\uDEAA Не согласен", "policy:decline")
                    .build();

            telegramSender.send(chatId, text, keyboard);
            return ctx.keep();
        };
    }
}
