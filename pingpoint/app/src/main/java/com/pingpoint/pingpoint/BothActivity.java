package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.parse.ParseUser;
import android.app.ActionBar;


/**
 * Activity which displays a registration screen to the user.
 */
public class BothActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_both);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        // Log in button click handler
        Button friendButton = (Button) findViewById(R.id.friend_button);
        friendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(BothActivity.this, FriendActivity.class));
            }
        });

        // Sign up button click handler
        Button signupButton = (Button) findViewById(R.id.group_button);
        signupButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(BothActivity.this, FunctionActivity.class));
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
    }
}
