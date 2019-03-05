package com.sharma.kuhuk.videochat;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import android.support.annotation.NonNull;
import android.Manifest;
import android.view.View;
import android.widget.FrameLayout;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sharma.kuhuk.videochat.HomeActivity.roomName;

public class VideoChat extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    private static final String TAG = VideoChat.class.getSimpleName();
    private static String API_KEY = "46276612"; //won't change
//    private static String SESSION_ID = "1_MX40NjI3NjYxMn5-MTU1MTE4NDU1MTcxMH55THY3NkF2Q3lzWmpQSTRNckZnVnI4L2h-fg";
//    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjI3NjYxMiZzaWc9MzBmMTE4MjI5NWNkMWFkOTA0ODI0YjhhNTJiNWM1ZmVhODMyZDJhNzpzZXNzaW9uX2lkPTFfTVg0ME5qSTNOall4TW41LU1UVTFNVEU0TkRVMU1UY3hNSDU1VEhZM05rRjJRM2x6V21wUVNUUk5ja1puVm5JNEwyaC1mZyZjcmVhdGVfdGltZT0xNTUxMTg0NTc4Jm5vbmNlPTAuNTU3NjUzMzU1MzI5NDczOCZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTUzNzcyOTgxJmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static final String LOG_TAG = VideoChat.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    String sessionId, token;
    FloatingActionButton btnEndCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_chat);

        // Hide the status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Hide the action bar
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.hide();

        requestPermissions();

//        btnEndCall = findViewById(R.id.btnEndCall);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = findViewById(R.id.publisher_container);
            mSubscriberViewContainer = findViewById(R.id.subscriber_container);

            // get sessionId and token values from server
            ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
            Call<ServerResponse> call = apiService.getRoomName(roomName);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    String sessionIdVal, tokenVal;

                    sessionIdVal = response.body().getSessionId();
                    tokenVal = response.body().getToken();
                    sessionId = sessionIdVal;
                    token = tokenVal;

                    Log.e(TAG, sessionIdVal);
                    Log.e(TAG, tokenVal);
                    Database.sessionData(sessionId, token);
                    getSession(sessionId, token);
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    void getSession (String sessionIdVal, final String tokenVal) {
        // initialize and connect to the session
        mSession = new Session.Builder(this, API_KEY, sessionIdVal).build();
        mSession.setSessionListener(this);
        mSession.connect(tokenVal);

        // to end call
//        btnEndCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mSession = null;
//                sessionId = null;
//                token = null;
//            }
//        });
    }

    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");

        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");
            if (mSubscriber != null) {
                mSubscriber = null;
                mSubscriberViewContainer.removeAllViews();
            }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
