package com.example.e_cynic.entityTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class AddressTest {
    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();
    private String TAG = "AddressTest";

    @Test
    public void addressConstructorTest() {
        Integer userId = 2;
        String firstLine = "Jalan Sunsuria";
        String secondLine = "Bandar Sunsuria";
        String thirdLine = "";
        String city = "Sepang";
        int postcode = 43900;
        String state = "Selangor";
        Address address = new Address(null, userId, firstLine, secondLine, thirdLine, city, state, postcode);
        String expected = "Address{id=null, firstLine='Jalan Sunsuria', secondLine='Bandar Sunsuria', " +
                "thirdLine='', city='Sepang', state='Selangor', postcode=43900}";
        String result = address.toString();
        LoggingUtil.printMessage(TAG, result);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getAddressString() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Address address = AddressDatabase.getAddressByAddressId(50);
        LoggingUtil.printMessage("get address string", address.getAddressString());
    }
}
