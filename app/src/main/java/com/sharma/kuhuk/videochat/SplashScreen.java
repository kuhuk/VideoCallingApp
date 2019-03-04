package com.sharma.kuhuk.videochat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide the action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

        sharedPreferences = getSharedPreferences(Constants.PREF_SP_FILE_NAME, MODE_PRIVATE);
        checkLogin = sharedPreferences.getString(Constants.PREF_USER_EMAIL, Constants.NA_DEFAULT);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkLogin != null && !checkLogin.equals(Constants.NA_DEFAULT)) {
                    startActivity(new Intent(SplashScreen.this, HomeActivity.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }
        }, 3000);
    }
}
