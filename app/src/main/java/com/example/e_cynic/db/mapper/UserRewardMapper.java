package com.example.e_cynic.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.e_cynic.entity.UserReward;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserRewardMapper {
    public static UserReward mapCursorToOneUserReward(Cursor cursor) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToOne(cursor, UserReward.class) : null;
    }

    public static List<UserReward> mapCursorToUserRewards(Cursor cursor) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToMany(cursor, UserReward.class) : null;
    }

    public static ContentValues mapUserRewardToContentValues(UserReward userReward) throws IllegalAccessException {
        return Mapper.mapEntityToContentValues(userReward);
    }
}
