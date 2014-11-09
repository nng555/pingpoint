package com.pingpoint.pingpoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.view.ViewGroup;
import android.view.View;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.model.Marker;
import android.location.Location;
import android.location.Criteria;
import android.location.LocationManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.IntentSender;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseGeoPoint;

import java.util.ArrayList;
import java.util.List;


public class FunctionActivity extends Activity
        implements GooglePlayServicesClient.ConnectionCallbacks,
                   GooglePlayServicesClient.OnConnectionFailedListener,
                   LocationListener, GoogleMap.OnMapClickListener{

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
    LatLng myPosition;
    private LocationClient mLocationClient;
    boolean mUpdatesRequested;
    LocationRequest mLocationRequest;
    UiSettings mapSettings;
    private PingGroup group;

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
        //theMap.setOnMarkerClickListener((OnMarkerClickListener) this);
        mapSettings = theMap.getUiSettings();
        theMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);

            //theMap.addMarker(new MarkerOptions().position(myPosition).title("fucker"));
            theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            theMap.setOnMapClickListener(this);

        }
        mLocationClient = new LocationClient(this, this, this);
        mUpdatesRequested = false;
        mLocationClient.connect();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        ParseQuery<PingGroup> query = ParseQuery.getQuery(PingGroup.class);
        query.whereEqualTo("open", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<PingGroup>() {
            @Override
            public void done(List<PingGroup> groups, ParseException error) {
                if(groups != null){
                    group = groups.get(0);
                }
            }
        });

    }

    public void updateData(){
        ArrayList<ParseGeoPoint> pings = group.getPing();
        for (int i=0; i < pings.size(); i++) {
            ParseGeoPoint pt = pings.get(i);
            LatLng ping = new LatLng(pt.getLatitude(), pt.getLongitude());
            theMap.addMarker(new MarkerOptions().position(ping).visible(true));
        }
    }


    public void onMapClick(LatLng ping)
    {
        updateData();
        theMap.addMarker(new MarkerOptions().position(ping).visible(true));
        group.addPing(new ParseGeoPoint(ping.latitude,ping.longitude));
        popup();
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
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        theMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void popup() {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Enter Text:")
                .setView(input)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        PingGroup group = new PingGroup();
                        group.setName(value);
                        group.setUser(ParseUser.getCurrentUser());
                        group.addMember(ParseUser.getCurrentUser());
                        group.saveInBackground();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }

    protected void onStart()
    {
        super.onStart();
        mLocationClient.connect();
    }
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        group.removeOpened(ParseUser.getCurrentUser());
        super.onStop();
    }
    @Override
    protected void onPause() {
        // Save the current setting for updates
        //mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        //mEditor.commit();
        group.removeOpened(ParseUser.getCurrentUser());
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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

    public boolean onMarkerClick (Marker marker) {
        return true;
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