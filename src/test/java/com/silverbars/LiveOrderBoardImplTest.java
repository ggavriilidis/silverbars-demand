package com.silverbars;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by gavriilgavriilidis
 */
public class LiveOrderBoardImplTest {

    LiveOrderBoard underTest = new LiveOrderBoardImpl();

    @Test
    public void registerOneBuyOrder() {
        underTest.registerOrder(new Order("user1",2.5,500,OrderType.BUY));
        List<OrderSummary> expectedList = new ArrayList<>();
        expectedList.add(new OrderSummary(2.5, 500, OrderType.BUY));
        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.BUY, expectedList);
            put(OrderType.SELL, new ArrayList<>());
        }};
        assertEquals(expected,underTest.getOrderSummary());
    }

    @Test
    public void registerMoreBuyOrders() {
        underTest.registerOrder(new Order("user1",2,400,OrderType.BUY));
        underTest.registerOrder(new Order("user2",2.5,500,OrderType.BUY));
        underTest.registerOrder(new Order("user3",4.5,300,OrderType.BUY));

        ArrayList<OrderSummary> expectedList = new ArrayList<>();

        expectedList.add(new OrderSummary(2.5, 500, OrderType.BUY));
        expectedList.add(new OrderSummary(2, 400, OrderType.BUY));
        expectedList.add(new OrderSummary(4.5, 300, OrderType.BUY));

        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.BUY, expectedList);
            put(OrderType.SELL, new ArrayList<>());
        }};

        assertEquals(expected, underTest.getOrderSummary());

    }

    @Test
    public void registerMoreBuyOrdersWithSamePrice() {
        underTest.registerOrder(new Order("user1",2,400,OrderType.BUY));
        underTest.registerOrder(new Order("user2",2.5,500,OrderType.BUY));
        underTest.registerOrder(new Order("user3",4.5,300,OrderType.BUY));
        underTest.registerOrder(new Order("user4",4.5,300,OrderType.BUY));
        underTest.registerOrder(new Order("user3",4.5,400,OrderType.BUY));

        ArrayList<OrderSummary> expectedList = new ArrayList<>();

        expectedList.add(new OrderSummary(2.5, 500, OrderType.BUY));
        expectedList.add(new OrderSummary(6.5, 400, OrderType.BUY));
        expectedList.add(new OrderSummary(9, 300, OrderType.BUY));

        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.BUY, expectedList);
            put(OrderType.SELL, new ArrayList<>());
        }};

        assertEquals(expected, underTest.getOrderSummary());

    }

    @Test
    public void registerOneSellOrder() {
        underTest.registerOrder(new Order("user1",3,600,OrderType.SELL));
        List<OrderSummary> expectedList = new ArrayList<>();
        expectedList.add(new OrderSummary(3, 600, OrderType.SELL));
        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedList);
            put(OrderType.BUY, new ArrayList<>());
        }};
        assertEquals(expected,underTest.getOrderSummary());
    }

    @Test
    public void registerMoreSellOrders() {
        underTest.registerOrder(new Order("user1",1,900,OrderType.SELL));
        underTest.registerOrder(new Order("user2",6,200,OrderType.SELL));
        underTest.registerOrder(new Order("user3",5,100,OrderType.SELL));

        ArrayList<OrderSummary> expectedList = new ArrayList<>();

        expectedList.add(new OrderSummary(5, 100, OrderType.SELL));
        expectedList.add(new OrderSummary(6, 200, OrderType.SELL));
        expectedList.add(new OrderSummary(1, 900, OrderType.SELL));

        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedList);
            put(OrderType.BUY, new ArrayList<>());
        }};

        assertEquals(expected, underTest.getOrderSummary());

    }

    @Test
    public void registerMoreSellOrdersWithSamePrice() {
        underTest.registerOrder(new Order("user1",1.5,700,OrderType.SELL));
        underTest.registerOrder(new Order("user5",3,900,OrderType.SELL));
        underTest.registerOrder(new Order("user6",6.7,700,OrderType.SELL));
        underTest.registerOrder(new Order("user7",4,900,OrderType.SELL));
        underTest.registerOrder(new Order("user3",90,200,OrderType.SELL));
        underTest.registerOrder(new Order("user4",3.3,100,OrderType.SELL));
        underTest.registerOrder(new Order("user4",3.3,100,OrderType.SELL));

        ArrayList<OrderSummary> expectedList = new ArrayList<>();

        expectedList.add(new OrderSummary(6.6, 100, OrderType.SELL));
        expectedList.add(new OrderSummary(90, 200, OrderType.SELL));
        expectedList.add(new OrderSummary(8.2, 700, OrderType.SELL));
        expectedList.add(new OrderSummary(7, 900, OrderType.SELL));

        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedList);
            put(OrderType.BUY, new ArrayList<>());
        }};

        assertEquals(expected, underTest.getOrderSummary());
    }

    @Test
    public void cancelANonExistingOrder(){
        underTest.cancelOrder(new Order("user1",1.5,700,OrderType.SELL));
        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, new ArrayList<>());
            put(OrderType.BUY, new ArrayList<>());
        }};
        assertEquals(expected, underTest.getOrderSummary());
    }

    @Test
    public void cancelAnExistingOrder(){
        underTest.registerOrder(new Order("user1",1.5,700,OrderType.SELL));
        underTest.cancelOrder(new Order("user1",1.5,700,OrderType.SELL));
        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, new ArrayList<>());
            put(OrderType.BUY, new ArrayList<>());
        }};
        assertEquals(expected, underTest.getOrderSummary());
    }

    @Test
    public void cancelMultipleOrders(){
        underTest.registerOrder(new Order("user1",1.5,700,OrderType.BUY));
        underTest.registerOrder(new Order("user1",1.5,800,OrderType.SELL));
        underTest.registerOrder(new Order("user2",1,300,OrderType.SELL));

        underTest.cancelOrder(new Order("user1",1.5,700,OrderType.BUY));
        underTest.cancelOrder(new Order("user2",1,300,OrderType.SELL));

        ArrayList<OrderSummary> expectedList = new ArrayList<>();

        expectedList.add(new OrderSummary(1.5, 800, OrderType.SELL));

        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedList);
            put(OrderType.BUY, new ArrayList<>());
        }};
        assertEquals(expected, underTest.getOrderSummary());
    }

    @Test
    public void registerMoreBuyAndSellOrdersWithSamePrice() {
        underTest.registerOrder(new Order("user1",1.5,700,OrderType.SELL));
        underTest.registerOrder(new Order("user5",3,900,OrderType.BUY));
        underTest.registerOrder(new Order("user6",6.7,700,OrderType.SELL));
        underTest.registerOrder(new Order("user7",4,900,OrderType.SELL));
        underTest.registerOrder(new Order("user3",90,200,OrderType.SELL));
        underTest.registerOrder(new Order("user4",1.75,100,OrderType.SELL));
        underTest.registerOrder(new Order("user4",1.2,700,OrderType.BUY));
        underTest.registerOrder(new Order("user5",1.75,100,OrderType.SELL));
        underTest.registerOrder(new Order("user4",3.3,900,OrderType.BUY));
        underTest.registerOrder(new Order("user4",5,700,OrderType.BUY));

        ArrayList<OrderSummary> expectedSellList = new ArrayList<>();
        expectedSellList.add(new OrderSummary(3.5, 100, OrderType.SELL));
        expectedSellList.add(new OrderSummary(90, 200, OrderType.SELL));
        expectedSellList.add(new OrderSummary(8.2, 700, OrderType.SELL));
        expectedSellList.add(new OrderSummary(4, 900, OrderType.SELL));

        ArrayList<OrderSummary> expectedBuyList = new ArrayList<>();
        expectedBuyList.add(new OrderSummary(6.3, 900, OrderType.BUY));
        expectedBuyList.add(new OrderSummary(6.2, 700, OrderType.BUY));


        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedSellList);
            put(OrderType.BUY, expectedBuyList);
        }};

        assertEquals(expected, underTest.getOrderSummary());
    }

    @Test
    public void registerAndCancelMoreBuyAndSellOrdersWithSamePrice() {
        underTest.registerOrder(new Order("user1",1.5,700,OrderType.SELL));
        underTest.registerOrder(new Order("user5",3,900,OrderType.BUY));
        underTest.registerOrder(new Order("user6",6.7,700,OrderType.SELL));
        underTest.cancelOrder(new Order("user1",1.5,700,OrderType.SELL));
        underTest.registerOrder(new Order("user7",4,900,OrderType.SELL));
        underTest.registerOrder(new Order("user3",90,200,OrderType.SELL));
        underTest.registerOrder(new Order("user4",1.75,100,OrderType.SELL));
        underTest.cancelOrder(new Order("user7",4,900,OrderType.SELL));
        underTest.registerOrder(new Order("user4",1.2,700,OrderType.BUY));
        underTest.registerOrder(new Order("user5",1.75,100,OrderType.SELL));
        underTest.cancelOrder(new Order("user4",1.2,700,OrderType.BUY));
        underTest.registerOrder(new Order("user4",3.3,900,OrderType.BUY));
        underTest.registerOrder(new Order("user4",5,700,OrderType.BUY));

        ArrayList<OrderSummary> expectedSellList = new ArrayList<>();
        expectedSellList.add(new OrderSummary(3.5, 100, OrderType.SELL));
        expectedSellList.add(new OrderSummary(90, 200, OrderType.SELL));
        expectedSellList.add(new OrderSummary(6.7, 700, OrderType.SELL));

        ArrayList<OrderSummary> expectedBuyList = new ArrayList<>();
        expectedBuyList.add(new OrderSummary(6.3, 900, OrderType.BUY));
        expectedBuyList.add(new OrderSummary(5, 700, OrderType.BUY));


        Map <OrderType,List<OrderSummary>> expected = new HashMap<OrderType,List<OrderSummary>>(){{
            put(OrderType.SELL, expectedSellList);
            put(OrderType.BUY, expectedBuyList);
        }};

        assertEquals(expected, underTest.getOrderSummary());
    }

}