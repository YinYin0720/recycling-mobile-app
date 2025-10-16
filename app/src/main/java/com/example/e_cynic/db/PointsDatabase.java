package com.example.e_cynic.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.mapper.PointsMapper;
import com.example.e_cynic.entity.Point;

public class PointsDatabase
{
    public static final String pointsTable = "points";
    public static final String userId = "userId";
    public static final String pointsEarned = "pointsEarned";
    public static final String date = "date";

    private static SQLiteDatabase db = DatabaseConnectionProvider.getDatabase(null);

    public static boolean insertPoint(Point point) throws IllegalAccessException {
        ContentValues cv = PointsMapper.mapPointToContentValues(point);
        long result = db.insert(pointsTable, null, cv);
        return result > 0;
    }
}
