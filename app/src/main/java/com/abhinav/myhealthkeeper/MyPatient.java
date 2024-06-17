package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MyPatient extends AppCompatActivity {


    FirebaseDatabase database;

    RecyclerView recyclerView;
    MyPatientAdapter adapter;

    ArrayList<ModelForMyPrescription> prescriptionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patient);


        recyclerView = findViewById(R.id.myPatientRecyclerView);
        database = FirebaseDatabase.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyPatientAdapter(this, prescriptionList);
        recyclerView.setAdapter(adapter);


        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        database.getReference().child("DocPrescList")
                .child(currentUser).addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            prescriptionList.clear();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                ModelForMyPrescription model = dataSnapshot.getValue(ModelForMyPrescription.class);
                                prescriptionList.add(model);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}