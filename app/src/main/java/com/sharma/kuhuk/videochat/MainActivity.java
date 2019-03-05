package com.sharma.kuhuk.videochat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide the action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

        //attach Register Fragment on this activity
        getSupportFragmentManager().beginTransaction().replace(R.id.container_register, new FragmentRegister()).commit();
    }
}
