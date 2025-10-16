package com.example.e_cynic.dbTests;

import android.database.sqlite.SQLiteDatabase;

import com.example.e_cynic.db.UserRewardDatabase;
import com.example.e_cynic.entity.UserReward;
import com.example.e_cynic.utils.DatabaseUtil;
import com.example.e_cynic.utils.DateUtil;
import com.example.e_cynic.utils.LoggingUtil;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class UserRewardDatabaseTest {

    private static SQLiteDatabase database = DatabaseUtil.getTestDatabase();

    private Integer rewardId = 1;
    private Integer userId = 1;
    private Long date = DateUtil.getCurrentTimestamp();
    private String rewardItem = "RM10 voucher";
    private Integer points = 1000;

    @Test
    public void insertUserReward() throws IllegalAccessException {
        UserReward userReward = new UserReward(null, userId, date, rewardItem, points);
        boolean result = UserRewardDatabase.insertUserReward(userReward);
        LoggingUtil.printMessage("insert user reward", (result == true) ? "true" : "false");
    }

    @Test
    public void getUserRewardsByUsername() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<UserReward> userRewardList = UserRewardDatabase.getUserRewardsByUsername("testuser");
        if (userRewardList == null) {
            LoggingUtil.printMessage("get user rewards by username", "no user rewards found");
            return;
        }
        for (UserReward u :
                userRewardList) {
            LoggingUtil.printMessage("get user rewards by username", u.toString());
        }
    }

    @Test
    public void getUserRewardsByUserId() throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<UserReward> userRewardList = UserRewardDatabase.getUserRewardsByUserId(1);
        if (userRewardList == null) {
            LoggingUtil.printMessage("get user rewards by username", "no user rewards found");
            return;
        }
        for (UserReward u :
                userRewardList) {
            LoggingUtil.printMessage("get user rewards by username", u.toString());
        }
    }
}