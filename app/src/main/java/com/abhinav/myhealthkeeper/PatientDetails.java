package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.abhinav.myhealthkeeper.databinding.ActivityMainBinding;
import com.abhinav.myhealthkeeper.databinding.ActivityPatientDetailsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class PatientDetails extends AppCompatActivity {

    String auth;

    String doctorName;

    FirebaseDatabase database;

    ActivityPatientDetailsBinding binding;

    public PatientDetails() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPatientDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        database = FirebaseDatabase.getInstance();

        database.getReference().child("Doctor")
                .child(auth).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            Map map = (Map) snapshot.getValue();
                            doctorName = Objects.requireNonNull(map.get("name")).toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        String senderId = getIntent().getStringExtra("senderId");
        String receiverId = getIntent().getStringExtra("receiverId");
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String age = getIntent().getStringExtra("age");
        String date = getIntent().getStringExtra("date");
        String parentId = getIntent().getStringExtra("parentId");




        binding.patientNameDocPres.setText(name);
        binding.patientAddressDocPres.setText(address);
        binding.patientAgeDocPres.setText(age);
        binding.appointmentDateDocPres.setText(date);






        binding.btnConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientDetails.this, Chat.class);
                intent.putExtra("name", name);
                intent.putExtra("receiverId", receiverId);
                intent.putExtra("senderId", senderId);
                intent.putExtra("senderName", doctorName);
                startActivity(intent);
            }
        });




        binding.btnPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientDetails.this, DoctorPrescribtion.class);
                intent.putExtra("receiverId", receiverId);
                intent.putExtra("senderId", senderId);
                intent.putExtra("parentId", parentId);
                intent.putExtra("patientName", name);
                intent.putExtra("doctorName", doctorName);
                intent.putExtra("date", date);
                intent.putExtra("age", age);


                startActivity(intent);

            }
        });



    }
}