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
import android.view.LayoutInflater;
import android.app.Fragment;
import android.view.ViewGroup;
import android.content.Context;



/**
 * Activity which displays a registration screen to the user.
 */
public class FriendActivity extends Fragment {


    private ListView mListView;
    private FriendAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_friends, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        mListView = (ListView) v.findViewById(R.id.friend_list);
        mAdapter = new FriendAdapter(v.getContext(), new ArrayList<PingFriends>());
        mListView.setAdapter(mAdapter);

        updateData();
        Button createfriendButton = (Button) v.findViewById(R.id.add_friend_button);
        updateData();
        createfriendButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                popup();
            }
        });
    }

    private void popup() {
        View v = getView();
        final EditText input = new EditText(v.getContext());
        new AlertDialog.Builder(v.getContext())
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
                        updateData();
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
