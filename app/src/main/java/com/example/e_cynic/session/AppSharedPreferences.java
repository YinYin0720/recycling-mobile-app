package com.example.e_cynic.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.autofill.UserData;

import com.example.e_cynic.db.SharedPreferencesDatabase;
import com.example.e_cynic.db.UserDatabase;
import com.example.e_cynic.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AppSharedPreferences {
    public static String sharedPreferencesFile;

    public static boolean updateUser(String username) {
        sharedPreferencesFile = SharedPreferencesDatabase.getFileByUsername(username); //get sp file name
        if(sharedPreferencesFile == "") {
            boolean result = SharedPreferencesDatabase.createNewSpFile(username);
            if(result == false) {
                return false;
            }
            updateUser(username);
        }
        return true;
    }

    public static String getSpFile() {
        return sharedPreferencesFile;
    }

    public static String getLoggedInUsername(Context context) {
        List<String> usernameList = UserDatabase.getAllUsername();
        SharedPreferences sp = null;
        if(usernameList == null) {
            return "";
        }
        for (String username : usernameList) {
            sp = context.getSharedPreferences(SharedPreferencesDatabase.getFileByUsername(username), Context.MODE_PRIVATE);
            if(sp.getBoolean("LOGIN",true)) {
                return sp.getString("USERNAME","");
            }
        }
        return "";
    }
}
