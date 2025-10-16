package com.example.e_cynic.utils.mapper;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CursorColumnsMapper {
    public static <T> T mapCursorColumnsToObject(Cursor cursor, T object) throws NoSuchFieldException,
            IllegalAccessException {
        List<String> column_names = Arrays.asList(cursor.getColumnNames());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Field field = object.getClass().getDeclaredField(column_names.get(i));
            Class<?> type = field.getType();
            if(type == java.lang.Integer.class) {
                field.set(object, cursor.getInt(i));
            } else if(type == java.lang.Double.class) {
                field.set(object, cursor.getDouble(i));
            } else if(type == java.lang.Float.class) {
                field.set(object, cursor.getFloat(i));
            } else if(type == java.lang.Long.class) {
                field.set(object, cursor.getLong(i));
            } else if(type == java.lang.Boolean.class) {
                field.set(object, Boolean.valueOf(cursor.getString(i)));
            } else if(type == byte[].class) {
                field.set(object, cursor.getBlob(i));
            } else {
                field.set(object, cursor.getString(i));
            }
        }
        return object;
    }
}
