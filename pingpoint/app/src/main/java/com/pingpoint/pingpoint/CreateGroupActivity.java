package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.parse.ParseUser;
import android.widget.EditText;



/**
 * Activity which displays a registration screen to the user.
 */
public class CreateGroupActivity extends Activity {


    private EditText groupnameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

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
    }
}
