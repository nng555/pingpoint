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
@ParseClassName("PingFriends")
public class PingFriends extends ParseObject {

    public PingFriends() {

    }

    public void setUser(String user) { put("base", user); }

    public ParseUser getUser() {return getParseUser("base"); }

    public void setFriend(String user) {
        put("friend", user);
    }

    public String getFriend() {return getString("friend");}

}
