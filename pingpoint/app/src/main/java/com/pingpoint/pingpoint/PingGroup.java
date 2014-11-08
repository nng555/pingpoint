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

    public void addFriend(ParseUser user) {
        ArrayList<ParseUser> friend = (ArrayList<ParseUser>) get("friends");
        if (friend != null) {
            friend.add(user);
            put("friends", friend);
            saveInBackground();
        } else {
            friend = new ArrayList<ParseUser>();
            friend.add(user);
            put("friends", friend);
            saveInBackground();
        }

    }

}
