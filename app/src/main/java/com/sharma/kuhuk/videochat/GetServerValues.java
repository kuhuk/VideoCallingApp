package com.sharma.kuhuk.videochat;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sharma.kuhuk.videochat.HomeActivity.roomName;

public class GetServerValues {

    public static String sessionId;
    public static String token;
    private final static String TAG = GetServerValues.class.getSimpleName();

    public static void getServerVal() {

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
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
