package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
public class GroupActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private GroupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        mListView = (ListView) findViewById(R.id.group_list);
        mAdapter = new GroupAdapter(this, new ArrayList<PingGroup>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        Button creategroupButton = (Button) findViewById(R.id.button2);

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
        final EditText input = new EditText(this);
        new AlertDialog.Builder(this)
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

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final PingGroup group = mAdapter.getItem(position);
        group.addOpened(ParseUser.getCurrentUser());
        group.saveInBackground();

        startActivity(new Intent(GroupActivity.this, FunctionActivity.class));
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
