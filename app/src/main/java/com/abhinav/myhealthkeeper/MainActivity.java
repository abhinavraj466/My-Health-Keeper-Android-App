package com.abhinav.myhealthkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abhinav.myhealthkeeper.R;
import com.abhinav.myhealthkeeper.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

   // FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        auth = FirebaseAuth.getInstance();

        String currentUser = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Common.myId = currentUser;

        startMyService(currentUser);






        binding.findDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FindDoctor.class));
            }
        });


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        binding.myappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyAppointment.class));
            }
        });


        binding.myPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MyPrescription.class));
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
}