package com.example.e_cynic.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.e_cynic.entity.Order;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class OrderMapper
{
    public static Order mapCursorToOneOrder(Cursor cursor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToOne(cursor, Order.class) : null;
    }

    public static List<Order> mapCursorToOrders(Cursor cursor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToMany(cursor, Order.class) : null;
    }

    public static ContentValues mapOrderToContentValues(Order order) throws IllegalAccessException {
        return Mapper.mapEntityToContentValues(order);
    }
}
