package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.parse.ParseUser;
import android.widget.EditText;
import android.app.ActionBar;



/**
 * Activity which displays a registration screen to the user.
 */
public class CreateGroupActivity extends Activity {


    private EditText groupnameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        groupnameEditText = (EditText) findViewById(R.id.groupname_edit_text);

        // Log in button click handler
        Button createButton = (Button) findViewById(R.id.creategroup_button);
        createButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                createGroup();
            }
        });
    }

    private void createGroup() {
        String name = groupnameEditText.getText().toString().trim();
        PingGroup group = new PingGroup();
        group.setName(name);
        group.setUser(ParseUser.getCurrentUser());
        group.saveEventually();
        startActivity(new Intent(CreateGroupActivity.this, GroupActivity.class));
    }
}
