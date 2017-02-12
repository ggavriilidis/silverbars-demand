package com.silverbars;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by gavriilgavriilidis
 */
public class LiveOrderBoardImpl implements LiveOrderBoard {

    private Queue<Order> individualBuyOrders = new ConcurrentLinkedQueue<>();
    private Queue<Order> individualSellOrders = new ConcurrentLinkedQueue<>();

    public void registerOrder(Order order) {
        actOnOrder(order, (o, orders )-> orders.add(o));
    }

    public void cancelOrder(Order order) {
        actOnOrder(order, (o, orders )-> orders.remove(o));
    }

    public Map<OrderType, List<OrderSummary>> getOrderSummary() {
        Map<OrderType,List<OrderSummary>> summarisedOrders = new HashMap<>();
        summarisedOrders.computeIfAbsent(OrderType.BUY, k -> summariseOrders(individualBuyOrders, OrderType.BUY));
        summarisedOrders.computeIfAbsent(OrderType.SELL, k -> summariseOrders(individualSellOrders, OrderType.SELL));
        return summarisedOrders;
    }

    private List<OrderSummary> summariseOrders(Queue<Order> orders, OrderType orderType) {
            return orders
                    .stream()
                    .map(Order::getSummary)
                    .collect(Collectors.groupingBy(OrderSummary::getPricePerKg, Collectors.summingDouble(OrderSummary::getQuantityInKg)))
                    .entrySet()
                    .stream()
                    .map(e -> new OrderSummary(e.getValue(), e.getKey(), orderType))
                    .sorted()
                    .collect(Collectors.toList());
    }

    private void actOnOrder(Order order, BiFunction<Order,Queue<Order>,Boolean> function){
        if(order.getOrderType() == OrderType.BUY) {
            function.apply(order,individualBuyOrders);
        } else {
            function.apply(order,individualSellOrders);
        }
    }
}
