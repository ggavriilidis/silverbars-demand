package com.silverbars;

import java.util.List;
import java.util.Map;

/**
 * Created by gavriilgavriilidis
 */
public interface LiveOrderBoard {

    /**
     * Registers an order to the live order board
     * @param order The order to register
     */
    void registerOrder(Order order);

    /**
     * Removes an order from the live order board
     * @param order The order to cancel
     */
    void cancelOrder(Order order);

    /**
     * Returns the order summary with the individual orders summarised, sorted based on price (BUY desc - SELL asc)
     * and categorised based on the order type.
     * @return a Map of order summaries in which the key is the order type so that
     * the orders are two separate sorted lists categorised based on whether they are BUY or SELL.
     * The assumption is that it makes more sense for the orders data to be presented in two separate categories
     * to the end users as it is easier to read rather than having them all together, since the order summary
     * shown on the live board shows just the aggregate amount in kg and the price.
     * Also the assumption is that only orders of the same type can be aggregated together.
     */
    Map<OrderType,List<OrderSummary>> getOrderSummary();


}
