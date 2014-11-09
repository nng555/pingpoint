package com.pingpoint.pingpoint;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import android.app.ActionBar;

import java.util.List;


/**
 * Activity which displays a registration screen to the user.
 */
public class BothActivity extends Activity{

    ActionBar.Tab friendTab, groupTab;
    Fragment fragment1 = new FriendActivity();
    Fragment fragment2 = new GroupActivity();

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_both);
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        */
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        friendTab = actionBar.newTab().setText("Friends");
        groupTab = actionBar.newTab().setText("Groups");

        friendTab.setTabListener(new TabListener(fragment1));
        groupTab.setTabListener(new TabListener(fragment2));

        actionBar.addTab(groupTab);
        actionBar.addTab(friendTab);

        // Sign up button click handler
        /*
        Button signupButton = (Button) findViewById(R.id.group_button);
        signupButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(BothActivity.this, GroupActivity.class));
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                ParseUser.logOut();
                // Start and intent for the dispatch activity
                Intent intent = new Intent(BothActivity.this, DispatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        */
    }
}
