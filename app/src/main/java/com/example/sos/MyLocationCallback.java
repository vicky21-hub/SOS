package com.example.sos;
import android.location.Location;
import com.example.sos.LocationHelper;
import android.os.Looper;
import com.google.android.gms.location.LocationCallback;


public interface MyLocationCallback {
    void onLocationResult(Location location);
}
