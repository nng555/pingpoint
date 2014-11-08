package com.pingpoint.pingpoint;

import android.app.Activity;
import android.app.Fragment;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.view.ViewGroup;
import android.view.View;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.Marker;
import android.location.Location;
import android.widget.Toast;
import android.content.IntentSender;


public class FunctionActivity extends Activity
        implements GooglePlayServicesClient.ConnectionCallbacks,
                   GooglePlayServicesClient.OnConnectionFailedListener,
                   LocationListener{

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    private GoogleMap theMap;
    private LocationClient mLocationClient;
    boolean mUpdatesRequested;
    private Location poo;
    LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        setUpMapIfNeeded();
        theMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(this, this, this);
        mUpdatesRequested = false;
        mLocationClient.connect();

        if(!fuckMeInAss())
        {
            poo = null;
        }
        if (poo != null) {
            poo = mLocationClient.getLastLocation();
            Marker newMarker = theMap.addMarker(new MarkerOptions().position(new LatLng(poo.getLatitude(),
                    poo.getLongitude())).visible(true));
        }




    }

    private boolean fuckMeInAss()
    {
        if(mLocationClient.isConnected())
            return true;
        else
            return false;
    }

    private void setUpMapIfNeeded()
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (theMap == null) {
            theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (theMap != null) {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }

    protected void onStart()
    {
        super.onStart();
        mLocationClient.connect();
    }
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onPause() {
        // Save the current setting for updates
        //mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        //mEditor.commit();
        super.onPause();
    }

    public void onConnected(Bundle ed)
    {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

        if (mUpdatesRequested) {
         //   mLocationClient.requestLocationUpdates(mLocationRequest, this);
        }
    }
    public void onDisconnected(){
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();

    }
    public void onConnectionFailed(ConnectionResult connectionResult){

    }

    public void onLocationChanged (Location location) {
        // Report to the UI that the location was updated
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String provider) {}
    public void onProviderEnabled(String provider) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}




    /* updateLocation()
     * - Get Own User Location
     * - Upload said Location to server
     * - Bring back list of EVERY thing in server (AKA GROUP)
     * - Display other locations as PINGS
     */

    /* */
}