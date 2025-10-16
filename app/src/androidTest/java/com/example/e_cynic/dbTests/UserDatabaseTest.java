package com.example.e_cynic.dbTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.User;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserDatabaseTest {

    private Integer userid  = 1;
    private String username = "testuser";
    private String email = "test12345@gmail.com";
    private String password = "testuser";
    private String phoneNumber = "12345";
    private User test_user = new User(userid, username, email, password, phoneNumber);

    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    @Test
    public void insertUser() throws IllegalAccessException, NoSuchMethodException {
        test_user.userId = null;
        boolean result = UserDatabase.insertUser(test_user);
        LoggingUtil.printMessage("insert user", result == true ? "true" : "false");
        Assert.assertEquals(true, result);
    }

    @Test
    public void getUserInfo_test() throws NoSuchMethodException, NoSuchFieldException, InstantiationException,
            IllegalAccessException, InvocationTargetException, IOException {
        User user = UserDatabase.getUserInfoByUsername(username);
        LoggingUtil.printMessage("Get User Info By Username", (user != null) ? user.toString() : "null");
    }

    @Test
    public void getIdByUsername_test() {
        int userid = UserDatabase.getUserIdByUsername(username);
        LoggingUtil.printMessage("Get Id By Username", String.valueOf(userid));
    }

    @Test
    public void verifyUser_test() {
        boolean isValid = UserDatabase.verifyUser(username, password);
        LoggingUtil.printMessage("Verify User", isValid == true ? "Valid" : "Not valid");
    }

    @Test
    public void getAllUsers_test() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {
        List<User> userList = UserDatabase.getAllUsers();
        for (User u : userList) {
            LoggingUtil.printMessage("get all users", u.toString());
        }
    }

    @Test
    public void editUserByUserId() throws IllegalAccessException, NoSuchMethodException {
        User user = new User(userid, username, email, password, phoneNumber);
        boolean result = UserDatabase.editUserByUserId(user);
        LoggingUtil.printMessage("edit user by userid", (result == true) ? "true" : "false");
    }
}
