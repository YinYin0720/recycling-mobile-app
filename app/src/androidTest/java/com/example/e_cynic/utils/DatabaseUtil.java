package com.example.e_cynic.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.DatabaseConnectionProvider;

public class DatabaseUtil {

    private static Context context = ContextUtil.getContext();
    private static SQLiteDatabase database;

    public static SQLiteDatabase getTestDatabase() {
        if(database == null) {
            synchronized (DatabaseUtil.class) {
                database = DatabaseConnectionProvider.getDatabase(context);
            }
        }
        return database;
    }
}
