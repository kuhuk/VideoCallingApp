package com.sharma.kuhuk.videochat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("{roomName}")
    Call<ServerResponse> getRoomName(@Path("roomName") String roomName);
}
