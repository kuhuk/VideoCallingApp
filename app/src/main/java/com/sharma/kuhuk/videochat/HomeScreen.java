package com.sharma.kuhuk.videochat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity {

    TextView tvLoggedInAs;
    Button btnLogout;
    FirebaseAuth firebaseAuth;
    String userName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide the action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

        setupUIViews();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user != null) {
//            // User is signed in
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            String uid = user.getUid();
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            String key = database.getReference("users").push().getKey();
//
//            Map<String, Object> childUpdates = new HashMap<>();
//            childUpdates.put( "uid", uid);
//            if (email != null) {
//                childUpdates.put("email", email);
//            }
//            if (name != null) {
//                childUpdates.put( "email", name);
//            }
//            // other properties here
//
//            database.getReference("users").push().setValue(childUpdates, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if (databaseError == null) {
//
//                    }
//                }
//            });
//        }

        userName = FragmentLogin.email;

        // logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Toast.makeText(HomeScreen.this, "Successfully logged out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeScreen.this, MainActivity.class));
            }
        });

        // set Text - "Logged in as --"
        tvLoggedInAs.setText("Logged in as "+userName);

        // set fragment of recycler view
        //attach fragment on this activity
        getSupportFragmentManager().beginTransaction().replace(R.id.container_contactList, new FragmentHomeScreen()).commit();
    }

    private void setupUIViews() {
        tvLoggedInAs = findViewById(R.id.tvLoggedInAs);
        btnLogout = findViewById(R.id.btnLogout);
    }

    //finish on back pressed

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
