package com.example.e_cynic.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

public class ContextUtil {
    private static Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

    public static Context getContext() {
        return context;
    }
}
