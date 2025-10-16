package com.example.e_cynic.utils.mapper;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.util.List;

public class ContentValuesMapper {
    public static ContentValues mapFieldsToContentValues(List<Field> fields, Object object) throws IllegalAccessException {
        ContentValues cv = new ContentValues();

        Class<?> fieldType;
        String fieldName;
        for (Field field : fields) {
            fieldType = field.getType();
            fieldName = field.getName();
            if (field.get(object) == null || field.get(object) == "") {
                continue;
            }
            if (fieldType == java.lang.Integer.class) {
                cv.put(fieldName, (Integer) field.get(object));
            } else if (fieldType == java.lang.Long.class) {
                cv.put(fieldName, (Long) field.get(object));
            } else if (fieldType == java.lang.Double.class) {
                cv.put(fieldName, (Double) field.get(object));
            } else if (fieldType == java.lang.Float.class) {
                cv.put(fieldName, (Float) field.get(object));
            } else if (fieldType == java.lang.Boolean.class) {
                cv.put(fieldName, (Boolean) field.get(object));
            } else if (fieldType == java.lang.Byte.class) {
                cv.put(fieldName, (Byte) field.get(object));
            } else if(fieldType == byte[].class) {
                cv.put(fieldName, (byte[]) field.get(object));
            } else {
                cv.put(fieldName, (String) field.get(object));
            }
        }
        return cv;
    }
}
