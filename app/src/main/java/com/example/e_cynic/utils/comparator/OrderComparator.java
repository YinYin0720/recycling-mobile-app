package com.example.e_cynic.utils.comparator;

import com.example.e_cynic.entity.Order;

import java.util.Comparator;

public class OrderComparator {
    public static Comparator<Order> NewestOrder = new Comparator<Order>()
    {
        @Override
        public int compare(Order o1, Order o2)
        {
            return o2.date.compareTo(o1.date);
        }
    };

    public static Comparator<Order> OldestOrder = new Comparator<Order>()
    {
        @Override
        public int compare(Order o1, Order o2)
        {
            return o1.date.compareTo(o2.date);
        }
    };
}
