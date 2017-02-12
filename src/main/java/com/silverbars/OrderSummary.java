package com.silverbars;


/**
 * Created by gavriilgavriilidis
 */
public class OrderSummary implements Comparable<OrderSummary>{

    private double quantityInKg;
    private int pricePerKg;
    private OrderType orderType;

    public OrderSummary(double quantityInKg, int pricePerKg, OrderType orderType) {
        this.quantityInKg = quantityInKg;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderSummary)) return false;

        OrderSummary that = (OrderSummary) o;

        if (Double.compare(that.quantityInKg, quantityInKg) != 0) return false;
        if (pricePerKg != that.pricePerKg) return false;
        return orderType == that.orderType;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantityInKg);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + pricePerKg;
        result = 31 * result + orderType.hashCode();
        return result;
    }

    @Override
    public int compareTo(OrderSummary other) {
        return orderType == OrderType.BUY ? Integer.compare(other.pricePerKg, pricePerKg) : Integer.compare(pricePerKg, other.pricePerKg);
    }

    @Override
    public String toString() {
        return quantityInKg + " kg for " + "£" + pricePerKg;
    }
}
