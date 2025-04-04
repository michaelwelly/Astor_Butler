package museon_online.astor_butler.finance.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class PaymentInvoice {

    private final PaymentMeta meta;
    private final BigDecimal amount;
    private final String paymentUrl;

    public String getContext() {
        return meta.getContext();
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}