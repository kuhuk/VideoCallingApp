package com.sharma.kuhuk.videochat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sharma.kuhuk.videochat.HomeActivity.roomName;

public class Database {

    static FirebaseDatabase firebaseDatabase, fdRoomDetails;
    static DatabaseReference databaseReference, drRoomDetails;


    public static void onRegister(String name, String email) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Registered Users");
        Timestamp ts=new Timestamp(System.currentTimeMillis());
        Date date=new Date(ts.getTime());
        databaseReference.child(String.valueOf(date)).push().setValue(name);
        databaseReference.child(String.valueOf(date)).push().setValue(email);
    }

    public static void sessionData(String sessionId, String token) {
        fdRoomDetails = FirebaseDatabase.getInstance();
        drRoomDetails = fdRoomDetails.getReference("RoomDetails");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        drRoomDetails.child(String.valueOf(date)).push().setValue(roomName);
        drRoomDetails.child(String.valueOf(date)).push().setValue(sessionId);
        drRoomDetails.child(String.valueOf(date)).push().setValue(token);
    }
}
