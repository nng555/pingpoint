package com.pingpoint.pingpoint;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;

/**
 * Data model for a post.
 */
@ParseClassName("PingLocation")
public class PingLocation extends ParseObject {

    public PingLocation() {

    }

    public void setName(String user) { put("name", user); }

    public String getName() {return getString("name"); }

    public void setLatitude(double lat) {
        put("latitude", lat);
    }

    public double getLatitude() {return getDouble("latitude");}

    public void setLongitude(double lng) {
        put("longitude", lng);
    }

    public double getLongitude() {return getDouble("longitude");}

    public void updatePosition(double latitude, double longitude) {
        put("latitude", latitude);
        put("longitude", longitude);
    }

}
