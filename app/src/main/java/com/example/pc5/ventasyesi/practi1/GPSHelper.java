package com.example.pc5.ventasyesi.practi1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by yesica on 26/07/2016.
 */
public class GPSHelper implements LocationListener {

    private Location location;
    private LocationManager locationManager;
    private static GPSHelper instancia;
    private static Context ctx;

    private GPSHelper() {
    }

    public static GPSHelper getInstancia(Context c) {

        if (instancia == null) {
            instancia = new GPSHelper();
            ctx = c;
        }
        return instancia;
    }

    public Location getLocation() {
        return location;
    }

    public void start(LocationManager locationManager) {
        this.locationManager = locationManager;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
