package ua.kpi.comsys.dop;

public sealed interface OrderStatus permits Pending, Paid, Shipped, Cancelled {
}
