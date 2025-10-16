package com.example.e_cynic.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class LocationUtil {
    public static Address getAddressByLongitudeLatitude(Context context, double longitude,
                                                        double latitude) throws IOException {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        return addresses.size() > 0 ? addresses.get(0) : null;
    }
}
