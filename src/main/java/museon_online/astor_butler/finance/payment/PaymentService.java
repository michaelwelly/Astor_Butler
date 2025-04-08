package museon_online.astor_butler.finance.payment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentInvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final TransactionLogRepository logRepository;
    private final TinkoffPayService tinkoffPayService;
    private final UserBalanceService balanceService;

    @Transactional
    public PaymentInvoice createInvoice(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PaymentInvoice invoice = new PaymentInvoice();
        invoice.setUser(user);
        invoice.setAmount(amount);
        invoice.setPaymentSystem("TINKOFF");
        invoice.setStatus(PaymentStatus.CREATED);
        invoice.setCreatedAt(LocalDateTime.now());

        return invoiceRepository.save(invoice);
    }

    @Transactional
    public void confirmPayment(Long invoiceId, String externalPaymentId) {
        PaymentInvoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        invoice.setStatus(PaymentStatus.PAID);
        invoice.setPaidAt(LocalDateTime.now());
        invoice.setExternalPaymentId(externalPaymentId);
        invoiceRepository.save(invoice);

        // Обновление баланса
        balanceService.incrementBalance(invoice.getUser().getId(), invoice.getAmount());

        // Прозрачный лог
        TransactionLog log = new TransactionLog();
        log.setUserId(invoice.getUser().getId());
        log.setAmount(invoice.getAmount());
        log.setStatus("PAID");
        log.setPaymentSystem(invoice.getPaymentSystem());
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
}