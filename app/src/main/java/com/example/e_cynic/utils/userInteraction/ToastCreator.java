package com.example.e_cynic.utils.userInteraction;

import android.content.Context;
import android.widget.Toast;

public class ToastCreator
{
    public ToastCreator() {}

    public void createToast(Context context,String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
