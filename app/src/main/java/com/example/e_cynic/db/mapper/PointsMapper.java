package com.example.e_cynic.db.mapper;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.e_cynic.entity.Point;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PointsMapper
{
    public static Point mapCursorToOnePoint(Cursor cursor) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToOne(cursor, Point.class) : null;
    }

    public static List<Point> mapCursorToPoint(Cursor cursor) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        return (cursor.moveToFirst()) ? Mapper.mapCursorToMany(cursor, Point.class) : null;
    }

    public static ContentValues mapPointToContentValues(Point point) throws IllegalAccessException {
        return Mapper.mapEntityToContentValues(point);
    }
}
