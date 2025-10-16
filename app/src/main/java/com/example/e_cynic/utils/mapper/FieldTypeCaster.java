package com.example.e_cynic.utils.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FieldTypeCaster {
    public static Object parseValueToType(String value, Class<?> type) throws IOException {
        if(type == java.lang.Integer.class) {
            return Integer.parseInt(value);
        }
        else if(type == java.lang.Double.class) {
            return Double.parseDouble(value);
        }
        else if(type == java.lang.Float.class) {
            return Float.parseFloat(value);
        }
        else if(type == java.lang.Long.class) {
            return Long.valueOf(value);
        }
        else if(type == java.lang.String.class) {
            return String.valueOf(value);
        }
        else if(type == java.lang.Boolean.class) {
            return Boolean.valueOf(value);
        } else if(type == byte[].class) {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream objectOutput = new ObjectOutputStream(byteOutput);
            objectOutput.writeObject(value);
            objectOutput.flush();
            return byteOutput.toByteArray();
        }
        else {
            return type.cast(value);
        }
    }
}
