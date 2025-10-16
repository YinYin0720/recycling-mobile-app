package com.example.e_cynic.dbTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.OrderDatabase;
import com.example.e_cynic.entity.Order;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

public class OrderDatabaseTest {

    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    private String username = "testuser";
    private Integer orderId = 1;
    private Integer userId = 1;

    @Test
    public void insertOrder() throws IllegalAccessException {
        Order order = new Order(null, userId,1, DateUtil.getCurrentTimestamp(), null);
        boolean result = OrderDatabase.insertOrder(order);
        LoggingUtil.printMessage("insert order", (result == true) ? "true" : "false");
    }

    @Test
    public void getOrdersByUsername() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
       List<Order> orderList = OrderDatabase.getOrdersByUsername(username);
        if(orderList == null) {
            LoggingUtil.printMessage("get orders by username", "no orders exist");
            return;
        }
        for (Order o :
                orderList) {
            LoggingUtil.printMessage("get orders by username", o.toString());
        }
    }

    @Test
    public void getOrderByOrderId() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Order order = OrderDatabase.getOrderByOrderId(orderId);
        LoggingUtil.printMessage("get order by orderId", (order != null) ? order.toString() : "null");
    }

    @Test
    public void getOrdersByUserId() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<Order> orderList = OrderDatabase.getOrdersByUserId(userId);
        if(orderList == null) {
            LoggingUtil.printMessage("get orders by userid", "no orders exist");
            return;
        }
        for (Order o :
                orderList) {
            LoggingUtil.printMessage("get orders by userid", o.toString());
        }
    }

    @Test
    public void editOrderByOrderId() throws IllegalAccessException {
        Order order = new Order(orderId, 1,3, DateUtil.getCurrentTimestamp(), "Processing") ;
        boolean result = OrderDatabase.editOrderByOrderId(order);
        LoggingUtil.printMessage("edit order by orderid", (result == true) ? "true" : "false");
    }
}