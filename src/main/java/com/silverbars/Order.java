package com.silverbars;


/**
 * Created by gavriilgavriilidis
 */
public class Order {

    private String userId;

    private double quantityInKg;

    private int pricePerKg;

    private OrderType orderType;


    public Order(String userId, double quantityInKg, int pricePerKg, OrderType orderType) {
        this.userId = userId;
        this.quantityInKg = quantityInKg;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public double getQuantityInKg() {
        return quantityInKg;
    }

    public int getPricePerKg() {
        return pricePerKg;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderSummary getSummary() {
        return new OrderSummary(quantityInKg, pricePerKg, orderType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (Double.compare(order.quantityInKg, quantityInKg) != 0) return false;
        if (pricePerKg != order.pricePerKg) return false;
        if (!userId.equals(order.userId)) return false;
        return orderType == order.orderType;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId.hashCode();
        temp = Double.doubleToLongBits(quantityInKg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + pricePerKg;
        result = 31 * result + orderType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return orderType.getName() + ": " +
                quantityInKg + " kg " +
                " for £" + pricePerKg +
                " [" + userId + "]";
    }
}
