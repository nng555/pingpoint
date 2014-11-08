package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.ActionBar;
import android.widget.ListView;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity which displays a registration screen to the user.
 */
public class GroupActivity extends Activity {

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


        Button creategroupButton = (Button) findViewById(R.id.button2);

        updateData();
        creategroupButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this, CreateGroupActivity.class));
            }
        });
    }

    public void updateData(){
        ParseQuery<PingGroup> query = ParseQuery.getQuery(PingGroup.class);
        query.findInBackground(new FindCallback<PingGroup>() {

            @Override
            public void done(List<PingGroup> tasks, ParseException error) {
                if(tasks != null){
                    mAdapter.clear();
                    mAdapter.addAll(tasks);
                }
            }
        });
    }



}
