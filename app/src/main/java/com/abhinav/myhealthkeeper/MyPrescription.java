package com.abhinav.myhealthkeeper;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zego.ve.Log;

import java.util.ArrayList;
import java.util.Objects;

public class MyPrescription extends AppCompatActivity {

    FirebaseDatabase database;

    RecyclerView recyclerView;

    PatientMyPrescriptionAdapter adapter;

   ArrayList<ModelForMyPrescription> prescriptionList = new ArrayList<>();
   //ArrayList<String> keyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescription);

        recyclerView = findViewById(R.id.patientPrescriptionRecyclerView);
        database = FirebaseDatabase.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientMyPrescriptionAdapter(this, prescriptionList);
        recyclerView.setAdapter(adapter);


        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        database.getReference().child("PatPrescList")
                .child(currentUser).addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                       prescriptionList.clear();

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            ModelForMyPrescription model = dataSnapshot.getValue(ModelForMyPrescription.class);
                            prescriptionList.add(model);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





//        for (int i=0; i<keyList.size(); i++){
//
//            String key1 = keyList.get(i);
//
//            if (key1 != null){
//
//                database.getReference().child("PatAppointmentList").child(currentUser)
//                        .child(key1).addValueEventListener(new ValueEventListener() {
//                            @SuppressLint("NotifyDataSetChanged")
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                ModelForMyPrescription model = snapshot.getValue(ModelForMyPrescription.class);
//                                           assert model != null;
////                                           model.setPatId(key);
//                                           prescriptionList.add(model);
//
//                                           adapter.notifyDataSetChanged();
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//            }
//        }
//



//                       //Log.d(TAG, key);
//
//                       if (key!=null) {
//                           database.getReference().child("PatAppointmentList").child(currentUser)
//                                   .child(key).addValueEventListener(new ValueEventListener() {
//
//                                       @SuppressLint("NotifyDataSetChanged")
//                                       @Override
//                                       public void onDataChange(@NonNull DataSnapshot snapshot1) {
//
//
//                                           ModelForMyPrescription model = snapshot1.getValue(ModelForMyPrescription.class);
//                                           assert model != null;
//                                           model.setPatId(key);
//                                           prescriptionList.add(model);
//
//
//                                          // adapter.notifyDataSetChanged();
//                                       }
//
//                                       @Override
//                                       public void onCancelled(@NonNull DatabaseError error1) {
//
//                                           Toast.makeText(MyPrescription.this, error1.getMessage(), Toast.LENGTH_SHORT).show();
//                                       }
//                                   });
//                       }
//                   }
//               }
//
//                adapter.notifyDataSetChanged();
//
//            }
//
//
//        });
    }
}