package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DoctorMyAppointment extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModalForDoctorMyAppointment> list = new ArrayList<>();
    DatabaseReference databaseReference;

    DoctorMyAppointmentAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_my_appointment);


        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        recyclerView = findViewById(R.id.doctorMyAppointmentRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DocAppointmentList").child(currentuser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorMyAppointmentAdapter(list, this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    ModalForDoctorMyAppointment modal = dataSnapshot.getValue(ModalForDoctorMyAppointment.class);
                    assert modal != null;
                    modal.setUserId(dataSnapshot.getKey());
                    list.add(modal);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(DoctorMyAppointment.this, "Error in fetching the data", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DoctorMyAppointment.this, MainActivityDoctor.class));
    }
}