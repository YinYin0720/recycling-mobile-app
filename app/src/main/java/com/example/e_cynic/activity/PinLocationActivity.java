package com.example.e_cynic.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.e_cynic.R;
import com.example.e_cynic.constants.RequestCode;
import com.example.e_cynic.permission.Permissions;
import com.example.e_cynic.utils.LocationUtil;
import com.example.e_cynic.utils.userInteraction.ToastCreator;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class PinLocationActivity extends FragmentActivity implements OnMapReadyCallback
{
    private Location currentLocation;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient client;
    private TextView pinAddress;
    private Button confirmButton;
    private Address currentAddressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_location);

        pinAddress = findViewById(R.id.pin_address);
        confirmButton = findViewById(R.id.confirm_button);

        client = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        setConfirmButton();
    }

    private void fetchLastLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Permissions permissions = new Permissions();
            permissions.grantLocationPermission(this);
        }

        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
                if (location != null)
                {
                    currentLocation = location;
                    ToastCreator toastCreator = new ToastCreator();
                    toastCreator.createToast(getApplicationContext(),currentLocation.getLatitude()+""+currentLocation.getLongitude());
                }
                else {
                    //set location to XMUM
                    currentLocation = new Location("");
                    currentLocation.setLongitude(101.7069749970963);
                    currentLocation.setLatitude(2.832703706851475);
                }
                supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
                supportMapFragment.getMapAsync(PinLocationActivity.this);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your location").draggable(true);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
        googleMap.addMarker(markerOptions);

        try
        {
            updatePinLocation(latLng.latitude, latLng.longitude);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                try {
                    updatePinLocation(marker.getPosition().latitude, marker.getPosition().longitude);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case RequestCode.LOCATION_PERMISSION:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    fetchLastLocation();
                }
                break;
        }
    }

    private void updatePinLocation(double latitude, double longitude) throws IOException {
        LatLng newLatLng = new LatLng(latitude, longitude);
        currentAddressValue = LocationUtil.getAddressByLongitudeLatitude(PinLocationActivity.this
                , newLatLng.longitude, newLatLng.latitude);
        if(currentAddressValue != null) {
            pinAddress.setText(currentAddressValue.getAddressLine(0));
        }

    }

    private void setConfirmButton() {
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("firstLine",
                        currentAddressValue.getFeatureName() + " " + currentAddressValue.getThoroughfare()) ;
                intent.putExtra("secondLine", currentAddressValue.getSubLocality()) ;
                intent.putExtra("city", currentAddressValue.getLocality());
                intent.putExtra("postcode", currentAddressValue.getPostalCode());
                intent.putExtra("state", currentAddressValue.getAdminArea());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}