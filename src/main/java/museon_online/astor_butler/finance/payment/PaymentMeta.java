package museon_online.astor_butler.finance.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import museon_online.astor_butler.finance.balance.WalletType;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMeta {

    private UUID userId;
    private String username;
    private WalletType walletType;
    private String context;
    private String returnUrl;

}
