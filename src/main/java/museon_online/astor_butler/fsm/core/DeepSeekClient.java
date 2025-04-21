package museon_online.astor_butler.fsm.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeepSeekClient {

    @Value("${DEEPSEEK_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.deepseek.com/chat/completions";

    public String askZenBuddha(String currentState, String input) {
        HttpEntity<Map<String, Object>> entity = buildEntity(currentState, input);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("choices")) {
                throw new IllegalStateException("Empty or malformed DeepSeek response");
            }

            Object choicesObj = body.get("choices");
            if (choicesObj instanceof List<?> choices && !choices.isEmpty()) {
                Object firstChoice = choices.get(0);
                if (firstChoice instanceof Map<?, ?> choiceMap) {
                    Object messageObj = choiceMap.get("message");
                    if (messageObj instanceof Map<?, ?> messageMap) {
                        Object content = messageMap.get("content");
                        if (content instanceof String str) {
                            return str.trim();
                        }
                    }
                }
            }

            log.warn("Unable to parse DeepSeek response properly");
            return currentState;
        } catch (Exception e) {
            log.error("DeepSeek API error", e);
            return currentState;
        }
    }

    private HttpEntity<Map<String, Object>> buildEntity(String currentState, String input) {
        Map<String, Object> payload = Map.of(
                "model", "deepseek-chat",
                "stream", false,
                "messages", List.of(
                        Map.of("role", "system", "content", "Ты — FSM-помощник. Отвечай только именем следующего enum-состояния."),
                        Map.of("role", "user", "content", "Состояние: " + currentState + ". Ввод: " + input)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        return new HttpEntity<>(payload, headers);
    }
}