package com.example.e_cynic.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.e_cynic.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserMapper
{
    public static User mapCursorToOneUser(Cursor cursor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToOne(cursor, User.class) : null;
    }

    public static List<User> mapCursorToUsers(Cursor cursor) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToMany(cursor, User.class) : null;
    }

    public static ContentValues mapUserToContentValues(User user) throws IllegalAccessException {
        return Mapper.mapEntityToContentValues(user);
    }
}
