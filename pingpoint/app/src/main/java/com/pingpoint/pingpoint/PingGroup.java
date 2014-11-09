package com.pingpoint.pingpoint;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import com.google.android.gms.maps.model.Marker;
import java.util.Collections;

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
            this.saveInBackground();
        } else {
            member = new ArrayList<ParseUser>();
            member.add(user);
            put("members", member);
            this.saveInBackground();
        }
    }

    public void addOpened(ParseUser user) {
        ArrayList<String> opened = (ArrayList<String>) get("open");
        if (opened != null) {
            opened.add(user.getUsername());
            put("open", opened);
        } else {
            opened = new ArrayList<String>();
            opened.add(user.getUsername());
            put("open", opened);
        }
    }

    public void removeOpened(ParseUser user) {
        ArrayList<String> opened = (ArrayList<String>) get("open");
        int i = 0;
        while(i<opened.size()) {
            if(opened.get(i).equals(user.getUsername())) {
                opened.remove(i);
            } else {
                i++;
            }
        }
    }

}
