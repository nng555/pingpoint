package com.pingpoint.pingpoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.ActionBar;

/**
 * Activity which displays a registration screen to the user.
 */
public class GroupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Button creategroupButton = (Button) findViewById(R.id.button2);
        creategroupButton.setOnClickListener(new

        OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GroupActivity.this, CreateGroupActivity.class));
            }
        });
    }



}
