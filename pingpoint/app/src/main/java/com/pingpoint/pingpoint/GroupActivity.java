package com.pingpoint.pingpoint;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.app.ActionBar;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.GetCallback;
import com.parse.FindCallback;
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
public class GroupActivity extends Fragment{


    private ListView mListView;
    private GroupAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_groups, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getView();
        mListView = (ListView) v.findViewById(R.id.group_list);
        mAdapter = new GroupAdapter(v.getContext(), new ArrayList<PingGroup>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(
            new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    final PingGroup group = mAdapter.getItem(position);
                    group.addOpened(ParseUser.getCurrentUser());
                    group.saveInBackground();

                    startActivity(new Intent(getActivity(), FunctionActivity.class));
                }
            });

        Button creategroupButton = (Button) v.findViewById(R.id.button2);

        updateData();
        creategroupButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                popup();
                updateData();
            }
        });
    }



    public void popup() {
        View v = getView();
        final EditText input = new EditText(v.getContext());
        new AlertDialog.Builder(v.getContext())
                .setTitle("Create a group:")
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        PingGroup group = new PingGroup();
                        group.setName(value);
                        group.setUser(ParseUser.getCurrentUser());
                        group.addMember(ParseUser.getCurrentUser());
                        group.saveInBackground();
                        updateData();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }

    public void updateData(){
        ParseQuery<PingGroup> query = ParseQuery.getQuery(PingGroup.class);
        query.whereEqualTo("members", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<PingGroup>() {
            @Override
            public void done(List<PingGroup> groups, ParseException error) {
                if(groups != null){
                    mAdapter.clear();
                    mAdapter.addAll(groups);
                }
            }
        });
    }



}
