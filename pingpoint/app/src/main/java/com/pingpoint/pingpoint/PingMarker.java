package com.pingpoint.pingpoint;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import com.google.android.gms.maps.model.Marker;

/**
 * Data model for a post.
 */
@ParseClassName("PingMarker")
public class PingMarker extends ParseObject {

    public PingMarker() {

    }

    public void setValue(String value) {
        put("value",value);
    }

    public void setGroup(String name) {
        put("group",name);
    }

    public String getValue() {
        return getString("value");
    }

    public String getGroup() {
        return getString("group");
    }

    public void setLatitude(double lat) {
        put("latitude",lat);
    }

    public void setLongitude(double lng) {
        put("longitude",lng);
    }

    public double getLatitude() {
        return getDouble("latitude");
    }

    public double getLongitude() {
        return getDouble("longitude");
    }

}
