package com.example.e_cynic.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.e_cynic.entity.Address;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AddressMapper
{
    public static Address mapCursorToOneAddress(Cursor cursor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToOne(cursor, Address.class) : null;
    }

    public static List<Address> mapCursorToAddress(Cursor cursor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToMany(cursor, Address.class) : null;
    }

    public static ContentValues mapAddressToContentValues(Address address) throws IllegalAccessException {
        return Mapper.mapEntityToContentValues(address);
    }
}
