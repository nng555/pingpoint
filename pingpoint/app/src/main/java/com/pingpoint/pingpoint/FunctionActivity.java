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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.graphics.Bitmap;

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
import com.parse.ParseUser;
import com.google.maps.android.ui.IconGenerator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;


public class FunctionActivity extends Activity
        implements GooglePlayServicesClient.ConnectionCallbacks,
                   GooglePlayServicesClient.OnConnectionFailedListener,
                   LocationListener, GoogleMap.OnMapClickListener,
                   OnMarkerClickListener{

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
    private IconGenerator icnGenerator;
    private PingLocation me;
    private Marker myMarker;
    private ArrayList<ParseUser> members;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        */
        theMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        setUpMapIfNeeded();
        //theMap.setOnMarkerClickListener((OnMarkerClickListener) this);
        mapSettings = theMap.getUiSettings();
        theMap.setMyLocationEnabled(true);
        icnGenerator = new IconGenerator(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
            ParseQuery<PingLocation> query = ParseQuery.getQuery(PingLocation.class);
            query.whereEqualTo("name", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<PingLocation>() {
                @Override
                public void done(List<PingLocation> locations, ParseException error) {
                    if(locations.size() == 0){
                        me = new PingLocation();
                        me.setName(ParseUser.getCurrentUser().getUsername());
                        me.setLatitude(myPosition.latitude);
                        me.setLongitude(myPosition.longitude);
                        me.saveInBackground();
                    } else {
                        me = locations.get(0);
                        me.setLatitude(myPosition.latitude);
                        me.setLongitude(myPosition.longitude);
                        me.saveInBackground();
                    }
                }
            });

            theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            theMap.setOnMapClickListener(this);
            theMap.setOnMarkerClickListener(this);

        }
        mLocationClient = new LocationClient(this, this, this);
        mUpdatesRequested = false;
        mLocationClient.connect();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        ParseQuery<PingGroup> query = ParseQuery.getQuery(PingGroup.class);
        query.whereEqualTo("open", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<PingGroup>() {
            @Override
            public void done(List<PingGroup> groups, ParseException error) {
                if (groups != null) {
                    group = groups.get(0);
                    group.removeOpened(ParseUser.getCurrentUser());
                    group.saveInBackground();
                    setTitle(group.getName());
                    members = group.getMember();
                    updateData();
                }
            }
        });


        theMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                updateMyLocation(location);
                updateData();
            }
        });
    }

    public void updateMyLocation(Location location) {

        ParseQuery<PingMarker> query = ParseQuery.getQuery(PingMarker.class);
        query.whereEqualTo("value", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<PingMarker>() {
                                   @Override
                                   public void done(List<PingMarker> meMarkers, ParseException error) {
                                        if(meMarkers.size() != 0) {
                                            for(int i = 0; i < meMarkers.size(); i++) {
                                                meMarkers.get(i).deleteInBackground();
                                            }
                                        }
                                   }
                               });
        PingMarker marker = new PingMarker();
        marker.setGroup(group.getName());
        marker.setValue(ParseUser.getCurrentUser().getUsername());
        marker.setLongitude(location.getLongitude());
        marker.setLatitude(location.getLatitude());
        marker.saveInBackground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.add_friend_button:
                addFriendPopup();
                break;
            case R.id.refresh_button:
                updateData();
                break;
            default:
                break;
        }

        return true;
    }

    private void addFriendPopup() {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add friend:")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("username", value);
                        query.findInBackground(new FindCallback<ParseUser>() {
                            @Override
                            public void done(List<ParseUser> members, ParseException error) {
                                if (members != null) {
                                    ParseUser member = members.get(0);
                                    group.addMember(member);
                                }
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }

    public void updateData() {
        theMap.clear();
        ParseQuery<PingMarker> query = ParseQuery.getQuery(PingMarker.class);
        query.whereEqualTo("group", group.getName());
        query.findInBackground(new FindCallback<PingMarker>() {
            @Override
            public void done(List<PingMarker> markers, ParseException error) {
                if (markers != null) {
                    for (int i = 0; i < markers.size(); i++) {
                        PingMarker marker = markers.get(i);
                        LatLng ping1 = new LatLng(marker.getLatitude(), marker.getLongitude());
                        theMap.addMarker(new MarkerOptions().position(ping1).visible(true)
                                .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon(marker.getValue()))));
                    }
                }
            }
        });
    }



    public void onMapClick(LatLng ping)
    {
        popup(ping);
        updateData();
    }

    public boolean onMarkerClick(Marker fgt){
        final EditText input = new EditText(this);
        final Marker mark = fgt;
        new AlertDialog.Builder(this)
        .setTitle("Change Marker Name:")
        .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    mark.setIcon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon(value)));
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                    ParseQuery<PingMarker> query = ParseQuery.getQuery(PingMarker.class);
                    query.whereEqualTo("latitude", mark.getPosition().latitude);
                    query.findInBackground(new FindCallback<PingMarker>() {
                        @Override
                        public void done(List<PingMarker> markers, ParseException error) {
                            if(markers != null){
                                markers.get(0).deleteInBackground();
                                mark.remove();
                                updateData();
                            }
                        }
                    });
            }
        }).show();
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
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        theMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void popup(LatLng ping) {
        final LatLng ping1 = ping;
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Enter Text:")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        theMap.addMarker(new MarkerOptions().position(ping1).visible(true)
                                .icon(BitmapDescriptorFactory.fromBitmap(icnGenerator.makeIcon(value)))
                                .draggable(true));
                        PingMarker marker = new PingMarker();
                        marker.setGroup(group.getName());
                        marker.setValue(value);
                        marker.setLongitude(ping1.longitude);
                        marker.setLatitude(ping1.latitude);
                        marker.saveInBackground();
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
        super.onStop();
    }
    @Override
    protected void onPause() {
        // Save the current setting for updates
        //mEditor.putBoolean("KEY_UPDATES_ON", mUpdatesRequested);
        //mEditor.commit();
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


    public void onProviderDisabled(String provider) {}
    public void onProviderEnabled(String provider) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}

}