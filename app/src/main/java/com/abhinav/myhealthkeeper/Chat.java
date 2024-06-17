package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.abhinav.myhealthkeeper.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class Chat extends AppCompatActivity {

    ActivityChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;



    //String senderId, receiverId, name, senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String userId = auth.getUid();



        String senderId = getIntent().getStringExtra("senderId");
        String receiverId = getIntent().getStringExtra("receiverId");
        String name = getIntent().getStringExtra("name");
        String senderName = getIntent().getStringExtra("senderName");



        binding.name.setText(name);

        final ArrayList<MessageModel> messageModels = new ArrayList<>();


        final  ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);
        binding.patChatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.patChatRecyclerView.setLayoutManager(layoutManager);


        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;




        database.getReference().child("chats").child(senderRoom)
                        .addValueEventListener(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                messageModels.clear();

                                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                                    MessageModel model = snapshot1.getValue(MessageModel.class);
                                    messageModels.add(model);
                                }

                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {


                            }
                        });





        binding.patChatSendMessageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = binding.patEnterMsg.getText().toString();

                if (!message.equals("")){

                    final MessageModel model = new MessageModel(userId, message);
                    model.setTimestamp(new Date().getTime());
                    binding.patEnterMsg.setText("");

                    database.getReference().child("chats")
                            .child(senderRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    database.getReference().child("chats")
                                            .child(receiverRoom)
                                            .push()
                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
                }

            }
        });






        // -----------------------Video Call--------------------------



//        binding.videoCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
////                database.getReference().child("DocAppointmentList")
////                        .child(senderId).child(receiverId)
////                        .addValueEventListener(new ValueEventListener() {
////                            @Override
////                            public void onDataChange(@NonNull DataSnapshot snapshot) {
////
////                                String targetUserId = (String)snapshot.child("parentId").getValue();
//////                                Intent intent = new Intent(Chat.this, VideoCall.class);
//////                                intent.putExtra("targetUserId", targetUserId);
//////                                startActivity(intent);
////
////                                if (Common.ROLE.equals("Doctor")){
////
////                                    setVideoCall(targetUserId);
////
////                                }
////
////                            }
////
////                            @Override
////                            public void onCancelled(@NonNull DatabaseError error) {
////
////                            }
////                        });
//
//
//            }
//        });


        if (Common.ROLE.equals("Doctor")){

            database.getReference().child("DocAppointmentList")
                    .child(senderId).child(receiverId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String targetUserId = (String)snapshot.child("parentId").getValue();
//                                Intent intent = new Intent(Chat.this, VideoCall.class);
//                                intent.putExtra("targetUserId", targetUserId);
//                                startActivity(intent);

                            setVideoCall(targetUserId);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }


    }

    public  void setVideoCall(String targetUserId){
       binding.btnVideoCall.setIsVideoCall(true);
       binding.btnVideoCall.setResourceID("zego_uikit_call");
       binding.btnVideoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId, targetUserId)));
    }


}