package com.sharma.kuhuk.videochat;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    Button btnVideoChat;
    EditText etRoomName;
    public static String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupUIViews();

        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide the action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

            btnVideoChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        Log.e(TAG, "roomName is : " +roomName);
                        startActivity(new Intent(HomeActivity.this, VideoChat.class));
                    }
                }
            });
    }

    private void setupUIViews() {
        etRoomName = findViewById(R.id.etRoomName);
        btnVideoChat = findViewById(R.id.btnVideoChat);
    }

    private Boolean validate() {
        Boolean result = false;

        roomName = etRoomName.getText().toString();

        if (roomName.isEmpty())
            Toast.makeText(this, "Please enter a room email.", Toast.LENGTH_SHORT).show();
        else
            result = true;
        return result;
    }
}
