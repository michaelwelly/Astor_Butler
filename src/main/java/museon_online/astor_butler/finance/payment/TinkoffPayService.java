package museon_online.astor_butler.finance.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TinkoffPayService {

    public PaymentInvoice createInvoice(UUID userId, BigDecimal amount, String description) {
        // TODO: Реальная логика вызова Tinkoff API и формирования ссылки
        String fakeUrl = "https://pay.tinkoff.ru/fake-link-for-" + userId;
        return new PaymentInvoice(userId, amount, description, fakeUrl);
    }

    public PaymentStatus checkStatus(String externalPaymentId) {
        // TODO: Реальная проверка статуса через API Tinkoff
        return PaymentStatus.PAID; // заглушка
    }
}