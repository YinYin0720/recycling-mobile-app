package com.example.e_cynic.dbTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.AddressDatabase;
import com.example.e_cynic.entity.Address;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AddressDatabaseTest {
    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    private Integer userId = 1;
    private Integer addressId = 9;
    private String line1 = "No 1, Jalan Sunsuria," ;
    private String line2 = "Bandar Sunsuria";
    private String line3 = "";
    private String state = "Selangor";
    private String city = "Sepang";
    private Integer postcode = 43900;
    private Address address = new Address(null, userId, line1, line2, line3, city, state, postcode);

    @Test
    public void insertAddress() throws IllegalAccessException {
        boolean result = AddressDatabase.insertAddress(address);
        LoggingUtil.printMessage("insert address", (result == true)? "true" : "false");
    }

    @Test
    public void getAddressByAddressId() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        Address address = AddressDatabase.getAddressByAddressId(addressId);
        LoggingUtil.printMessage("get address by address id", (address != null) ? address.toString() :
                "null");
    }

    @Test
    public void getAddressesByUserId() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException, IOException {
        List<Address> addressList = AddressDatabase.getAddressesByUserId(userId);
        LoggingUtil.printMessage("get address by userid", (addressList != null) ? String.valueOf(addressList) : "null");
    }

    @Test
    public void getAddressesByUsername() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {
        List<Address> addressList = AddressDatabase.getAddressesByUsername("testuser");
        LoggingUtil.printMessage("get address by username", (addressList != null) ? String.valueOf(addressList) : "null");
    }

    @Test
    public void removeAddressByAddressId() {
        boolean result = AddressDatabase.removeAddressByAddressId(addressId);
        LoggingUtil.printMessage("remove address by addressid", (result == true) ? "true" : "false");
    }

    @Test
    public void editAddressByAddressId() throws IllegalAccessException, NoSuchMethodException {
        address.addressId = addressId;
        address.secondLine = "Taman Sunsuria2";
        boolean result = AddressDatabase.editAddressByAddressId(address);
        LoggingUtil.printMessage("edit address by addressid", (result == true) ? "true" : "false");
    }
}