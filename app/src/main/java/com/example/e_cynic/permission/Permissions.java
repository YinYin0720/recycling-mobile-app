package com.example.e_cynic.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;

import com.example.e_cynic.constants.RequestCode;

public class Permissions
{
    public Permissions()
    {

    }

    public void grantPhotoPermission(Context context)
    {
        ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, RequestCode.PHOTO_PERMISSION);
    }

    public void grantLocationPermission(Context context)
    {
        ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.LOCATION_PERMISSION);
    }
}
