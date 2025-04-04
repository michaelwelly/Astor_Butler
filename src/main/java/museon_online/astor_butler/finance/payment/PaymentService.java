package museon_online.astor_butler.finance.payment;

import lombok.RequiredArgsConstructor;
import museon_online.astor_butler.finance.balance.WalletType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TinkoffPayService tinkoffPayService;
    private final PaymentSessionRepository sessionRepository;

    public String createTinkoffPayment(UUID userId, BigDecimal amount, String context) {
        PaymentMeta meta = PaymentMeta.builder()
                .userId(userId)
                .username("anonymous") // TODO: прокинуть username из User при необходимости
                .walletType(WalletType.RUB)
                .context(context)
                .returnUrl("https://t.me/astor_butler_bot")
                .build();

        PaymentInvoice invoice = tinkoffPayService.createInvoice(meta, amount);

        PaymentSession session = PaymentSession.builder()
                .userId(userId)
                .amount(amount)
                .status(PaymentStatus.CREATED)
                .paymentUrl(invoice.getPaymentUrl())
                .provider("TINKOFF")
                .context(meta.getContext())
                .walletType(meta.getWalletType().name())
                .build();

        sessionRepository.save(session);
        return invoice.getPaymentUrl();
    }

    // TODO: реализовать updatePaymentStatus(sessionId, status)
}
