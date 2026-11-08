package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Paid(String paymentID, BigDecimal amount) implements OrderStatus {
    public Paid {
        if (paymentID == null || paymentID.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
