package com.example.e_cynic.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager
{
    // Initialize variable
    SharedPreferences sp;
    SharedPreferences.Editor edt;

    // Constructor
    public SessionManager(Context context)
    {
        sp = context.getSharedPreferences(AppSharedPreferences.getSpFile(),context.MODE_PRIVATE);
        edt = sp.edit();
        edt.apply();
    }

    // Set login
    public void setLogin(boolean login)
    {
        edt.putBoolean("LOGIN",login);
        edt.commit();
    }

    // Get login
    public boolean getLogin()
    {
        return sp.getBoolean("LOGIN",false);
    }

    // Get username
    public void setUsername(String uname)
    {
        edt.putString("USERNAME",uname);
        edt.commit();
    }

    // Get username
    public String getUsername()
    {
        return sp.getString("USERNAME","");
    }

    public void setTotalPoints(int totalPoints)
    {
        edt.putInt("TOTAL_POINTS",totalPoints);
        edt.commit();
    }

    public int getTotalPoints()
    {
        return sp.getInt("TOTAL_POINTS",0);
    }

    public void setCurrentDate(String dateTime)
    {
        edt.putString("DATE", dateTime);
        edt.commit();
    }

    public String getDate()
    {
        return sp.getString("DATE","");
    }
}
