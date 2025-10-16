package com.example.e_cynic.mapperTests;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.mapper.UserMapper;
import com.example.e_cynic.entity.User;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserMapperTest {

    private SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    private Integer userid  = 1;
    private String username = "testuser";
    private String email = "test@gmail.com";
    private String password = "testuser";
    private String phoneNumber = "12345";
    private User test_user = new User(userid, username, email, password, phoneNumber);

    @Test
    public void mapUserToContentValues() throws IllegalAccessException, NoSuchMethodException {
        ContentValues cv = UserMapper.mapUserToContentValues(test_user);
        LoggingUtil.printMessage("map user to content values", cv.toString());
    }

    @Test
    public void mapCursorToOneUser() throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        Cursor cursor = database.rawQuery("select * from users limit 1", null);
        User user = UserMapper.mapCursorToOneUser(cursor);
        LoggingUtil.printMessage("map to one user", user.toString());
    }

    @Test
    public void mapCursorToUsers() throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
        Cursor cursor = database.rawQuery("select * from users", null);
        List<User> userList = UserMapper.mapCursorToUsers(cursor);

        for (User u : userList) {
            LoggingUtil.printMessage("map to many users", u.toString());
        }
    }
}
