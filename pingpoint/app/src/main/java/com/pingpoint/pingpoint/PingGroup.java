package com.pingpoint.pingpoint;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Data model for a post.
 */
@ParseClassName("PingGroup")
public class PingGroup extends ParseObject {

    public PingGroup() {
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getName() { return getString("name"); }

    public void setUser(ParseUser user) { put("user", user); }

    public ParseUser getUser() {return getParseUser("user"); }

}
