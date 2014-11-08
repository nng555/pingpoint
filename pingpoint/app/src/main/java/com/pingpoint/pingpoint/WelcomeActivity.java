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
public class WelcomeActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);

    View decorView = getWindow().getDecorView();
    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
    decorView.setSystemUiVisibility(uiOptions);
    ActionBar actionBar = getActionBar();
    actionBar.hide();

    // Log in button click handler
    Button loginButton = (Button) findViewById(R.id.login_button);
    loginButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        // Starts an intent of the log in activity
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
      }
    });

    // Sign up button click handler
    Button signupButton = (Button) findViewById(R.id.signup_button);
    signupButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        // Starts an intent for the sign up activity
        startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
      }
    });
  }
}
