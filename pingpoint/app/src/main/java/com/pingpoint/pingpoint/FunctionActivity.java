package com.pingpoint.pingpoint;

import android.app.Activity;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.view.ViewGroup;
import android.view.View;
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


public class FunctionActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

    private GoogleMap theMap;
    private LocationClient mLocationClient;
    private Location poo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        setUpMapIfNeeded();
        theMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();
        if(poo != null) {
            Marker newMarker = theMap.addMarker(new MarkerOptions().position(new LatLng(poo.getLatitude(), poo.getLongitude())).visible(true));
        }


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
    public void onConnected(Bundle ed)
    {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        poo = new Location(mLocationClient.getLastLocation());
    }
    public void onDisconnected(){}
    public void onConnectionFailed(ConnectionResult connectionResult){

    }



    /* updateLocation()
     * - Get Own User Location
     * - Upload said Location to server
     * - Bring back list of EVERY thing in server (AKA GROUP)
     * - Display other locations as PINGS
     */

    /* */
}