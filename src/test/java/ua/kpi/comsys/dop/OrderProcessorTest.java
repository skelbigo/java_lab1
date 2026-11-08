package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderProcessorTest {
    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void processPending() {
        String expected = """
                Order Status: PENDING
                Details: Order is currently pending processing.
                """;
        assertEquals(expected, processor.process(new Pending()));
    }

    @Test
    void processPaid() {
        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY-001, Amount: $150.50
                """;
        assertEquals(expected,
                processor.process(new Paid("PAY-001", new BigDecimal("150.50"))));
    }

    @Test
    void processShipped() {
        String expected = """
                Order Status: SHIPPED
                Details: Tracking Code: TRACK-123, Dispatch Date: 2026-11-08
                """;
        assertEquals(expected,
                processor.process(new Shipped("TRACK-123", LocalDate.of(2026, 11, 8))));
    }

    @Test
    void processCancelled() {
        String expected = """
                Order Status: CANCELLED
                Details: Reason: Customer request
                """;
        assertEquals(expected, processor.process(new Cancelled("Customer request")));
    }

    @Test
    void rejectNullStatus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> processor.process(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }

    @Test
    void rejectInvalidPaymentId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid(null, BigDecimal.TEN));
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("   ", BigDecimal.TEN));
    }

    @Test
    void rejectInvalidAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY-001", null));
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY-001", BigDecimal.ZERO));
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY-001", new BigDecimal("-1")));
    }

    @Test
    void rejectInvalidTrackingCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped(null, LocalDate.of(2026, 11, 8)));
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped("   ", LocalDate.of(2026, 11, 8)));
    }

    @Test
    void rejectNullDispatchDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped("TRACK-123", null));
    }

    @Test
    void rejectInvalidCancellationReason() {
        assertThrows(IllegalArgumentException.class, () -> new Cancelled(null));
        assertThrows(IllegalArgumentException.class, () -> new Cancelled("   "));
    }
}
