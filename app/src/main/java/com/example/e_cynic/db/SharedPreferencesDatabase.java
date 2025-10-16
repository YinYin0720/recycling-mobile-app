package com.example.e_cynic.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SharedPreferencesDatabase {
    private static final String sharedPreferencesTable = "sharedPreferences";

    private static SQLiteDatabase db = DatabaseConnectionProvider.getDatabase(null);

    public static String getFileByUsername(String username) {
        Cursor c = db.rawQuery("select spFile from " + sharedPreferencesTable + " where username=?", new String[]{username});
        return (c.moveToNext()) ? c.getString(0) : "";
    }

    public static boolean createNewSpFile(String username) {
        Integer userId = UserDatabase.getUserIdByUsername(username);
        ContentValues cv = new ContentValues();
        cv.put("userId", userId);
        cv.put("username", username);
        cv.put("spFile", username);
        long result = db.insert(sharedPreferencesTable, null, cv);
        return result > 0;
    }
}
