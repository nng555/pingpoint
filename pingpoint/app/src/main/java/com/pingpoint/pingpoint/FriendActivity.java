package com.pingpoint.pingpoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.ActionBar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.widget.EditText;


/**
 * Activity which displays a registration screen to the user.
 */
public class FriendActivity extends Activity {


    private ListView mListView;
    private FriendAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        mListView = (ListView) findViewById(R.id.friend_list);
        mAdapter = new FriendAdapter(this, new ArrayList<PingFriends>());
        mListView.setAdapter(mAdapter);

        updateData();
        Button createfriendButton = (Button) findViewById(R.id.add_friend_button);

        createfriendButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                popup();
                updateData();
            }
        });
    }

    private void popup() {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Add a friend:")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String friend = input.getText().toString();
                        PingFriends friend1 = new PingFriends();
                        friend1.setFriend(friend);
                        friend1.setUser(ParseUser.getCurrentUser().getUsername());
                        friend1.saveInBackground();
                        PingFriends friend2 = new PingFriends();
                        friend2.setFriend(ParseUser.getCurrentUser().getUsername());
                        friend2.setUser(friend);
                        friend2.saveInBackground();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }

    public void updateData(){
        ParseQuery<PingFriends> query = ParseQuery.getQuery(PingFriends.class);
        query.whereEqualTo("base", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<PingFriends>() {
            @Override
            public void done(List<PingFriends> friend, ParseException error) {
                if(friend != null){
                    mAdapter.clear();
                    mAdapter.addAll(friend);
                }
            }
        });
    }

}
