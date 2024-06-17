package com.abhinav.myhealthkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class VideoCall extends AppCompatActivity {

    EditText userName;
    Button confirmButton;
    ZegoSendCallInvitationButton videoCallBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        userName = findViewById(R.id.editTextText);
        videoCallBtn = findViewById(R.id.btnVideoCall);
        confirmButton = findViewById(R.id.buttonConfirm);

        String targetUserId = getIntent().getStringExtra("targetUserId");
        userName.setText(targetUserId);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideoCall(targetUserId);
            }
        });
    }


    void setVideoCall(String targetUserId){
        videoCallBtn.setIsVideoCall(true);
        videoCallBtn.setResourceID("zego_uikit_call");
        videoCallBtn.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId, targetUserId)));
    }
}