package com.example.e_cynic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/YY HH:mm:ss");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentDate()
    {
        return dateFormat.format(new Date());
    }

    public static String getCurrentDateTime() {
        return dateTimeFormat.format(new Date().getTime());
    }

    public static String getDateTimeByTimestamp(Long timestamp) {
        return dateTimeFormat.format(new Date(timestamp).getTime());
    }

    public static Long getCurrentTimestamp() {
        return new Date().getTime();
    }

    public static String  getDateFromTimestamp(Long timestamp)
    {
        return dateFormat.format(timestamp);
    }

    public static String getDuration(String currentDate,String orderDate)
    {
        try
        {
            Date d1 = dateFormat.parse(currentDate);
            Date d2 = dateFormat.parse(orderDate);
            long diff = d1.getTime() - d2.getTime();
            String duration = String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return duration;
        }

        catch (ParseException e)
        {
            e.printStackTrace();
            return "Error";
        }
    }
}
