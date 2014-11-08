package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activity which displays a registration screen to the user.
 */
public class GroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Button creategroupButton = (Button) findViewById(R.id.button2);
        creategroupButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this, CreateGroupActivity.class));
            }
        });
    }



}
