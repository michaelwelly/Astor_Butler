package museon_online.astor_butler.fsm.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import museon_online.astor_butler.telegram.utils.BotResponse;
import museon_online.astor_butler.telegram.context.CommandContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FSMRouter {

    private final List<FSMHandler<?>> handlers;
    private final FSMStorage fsmStorage;

    /**
     * Основной метод FSMRouter. Обрабатывает входящий контекст и направляет его
     * соответствующему FSM-обработчику в зависимости от текущего состояния пользователя.
     *
     * @param context входящий контекст команды
     * @return ответ бота, сформированный соответствующим FSM-обработчиком
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public BotResponse route(CommandContext context) {
        for (FSMHandler handler : handlers) {
            if (handler.supports(context)) {
                Enum<?> currentState = fsmStorage.getState(context.getUserId());
                log.debug("FSMRouter: handling FSM with handler={} state={} user={}", handler.getClass().getSimpleName(), currentState, context.getUserId());
                return handler.handle(context, currentState);
            }
        }
        log.debug("FSMRouter: no matching FSM handler for userId={}", context.getUserId());
        return null;
    }

    /**
     * Сбрасывает текущее состояние FSM-пользователя.
     * Используется для выхода из FSM по команде /cancel или программно.
     *
     * @param userId идентификатор пользователя
     */
    public void resetFSM(Long userId) {
        log.info("FSMRouter: resetting FSM state for userId={}", userId);
        fsmStorage.clearState(userId);
    }
}