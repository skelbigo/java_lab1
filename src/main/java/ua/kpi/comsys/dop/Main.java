package ua.kpi.comsys.dop;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        OrderProcessor processor = new OrderProcessor();

        OrderStatus[] orders = {
                new Pending(),
                new Paid("PAY-001", new BigDecimal("150.50")),
                new Shipped("TRACK-123", LocalDate.of(2026, 11, 8)),
                new Cancelled("Customer request")
        };

        for (OrderStatus order : orders) {
            System.out.println(processor.process(order));
        }
    }
}
