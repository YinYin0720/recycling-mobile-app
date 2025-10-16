package com.example.e_cynic.utils;

import org.junit.Test;

public class DateUtilTest {

    @Test
    public void getCurrentDateTime() {
        LoggingUtil.printMessage("get current date time", DateUtil.getCurrentDateTime());
    }

    @Test
    public void getDateTimeByTimestamp() {
        LoggingUtil.printMessage("get date time by timestamp",
                DateUtil.getDateTimeByTimestamp(1442752952000l));
    }

    @Test
    public void getCurrentTimestamp() {
        LoggingUtil.printMessage("get current timestamp", String.valueOf(DateUtil.getCurrentTimestamp()));
    }
}