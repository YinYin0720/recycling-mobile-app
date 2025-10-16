package com.example.e_cynic.utils.userInteraction;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarCreator
{
    public SnackbarCreator() {}

    public static void createNewSnackbar(View view,String message)
    {
        Snackbar sb = Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        sb.show();
    }
}
