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

    public void addMember(ParseUser user) {
        ArrayList<ParseUser> member = (ArrayList<ParseUser>) get("members");
        if (member != null) {
            member.add(user);
            put("members", member);
            saveInBackground();
        } else {
            member = new ArrayList<ParseUser>();
            member.add(user);
            put("members", member);
            saveInBackground();
        }

    }

}
