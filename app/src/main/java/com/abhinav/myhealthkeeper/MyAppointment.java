package com.abhinav.myhealthkeeper;

import static com.abhinav.myhealthkeeper.R.id.patientAppointmentRecyclerView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyAppointment extends AppCompatActivity {

    RecyclerView recyclerView;

    PatientMyAppointmentAdapter adapter;

    DatabaseReference databaseReference;

    ArrayList<ModalForPatientMyAppointment> list = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);

        recyclerView = (RecyclerView) findViewById(patientAppointmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("PatAppointmentList").child(currentuser);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientMyAppointmentAdapter(list, this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    ModalForPatientMyAppointment modal = dataSnapshot.getValue(ModalForPatientMyAppointment.class);
                    assert modal != null;
                    modal.setUserId(dataSnapshot.getKey());
                    list.add(modal);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MyAppointment.this, "Error in fetching the data", Toast.LENGTH_SHORT).show();

            }
        });


    }





}




