package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.abhinav.myhealthkeeper.databinding.ActivityMainBinding;
import com.abhinav.myhealthkeeper.databinding.ActivityMainDoctorBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.plugin.common.IZegoUIKitSignalingPlugin;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

import java.util.Objects;


public class MainActivityDoctor extends AppCompatActivity {

    FirebaseAuth auth;

    ActivityMainDoctorBinding binding;

    FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String currentUser = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Common.myId = currentUser;
        startMyService(currentUser);

        database.getReference().child("Doctor")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String docName = (String) snapshot.child("name").getValue();

                        binding.doctorNameMain.setText(docName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.myAppointmentDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityDoctor.this, DoctorMyAppointment.class));
            }
        });


        binding.logoutDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                auth.signOut();
                startActivity(new Intent(MainActivityDoctor.this, LoginActivity.class));
            }
        });


        binding.myPatientDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivityDoctor.this, MyPatient.class));
            }
        });

        binding.profileDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivityDoctor.this, DoctorProfile.class));
            }
        });


    }

    public   void  startMyService(String userId){

        Application application = getApplication(); // Android's application context
        long appID = 1248855423;   // yourAppID
        String appSign = "c8131aa0d89b539ff722fa1b5bb52b942a1f735da19896574310a3f086b723b1";  // yourAppSign

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userId, userId,callInvitationConfig);
    }

    protected void onDestroy() {

        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}