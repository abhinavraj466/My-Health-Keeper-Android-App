package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.abhinav.myhealthkeeper.databinding.ActivityDoctorProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoctorProfile extends AppCompatActivity {

    ActivityDoctorProfileBinding binding;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();

        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        database.getReference().child("Doctor").child(currentUser)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       Map map = (Map) snapshot.getValue();

                        assert map != null;

                        if (map.containsKey("name")){

                            binding.editDoctorName.setText(Objects.requireNonNull(map.get("name")).toString());
                        }
                        else {
                            binding.editDoctorName.setText("");
                        }


                        if (map.containsKey("email")){

                            binding.editDoctorEmail.setText(Objects.requireNonNull(map.get("email")).toString());
                        }
                        else {
                            binding.editDoctorEmail.setText("");
                        }


                        if (map.containsKey("specialist")){

                            binding.editDoctorSpeciality.setText(Objects.requireNonNull(map.get("specialist")).toString());
                        }
                        else {
                            binding.editDoctorSpeciality.setText("");
                        }

                        if (map.containsKey("experience")){

                            binding.editDoctorWorkExperience.setText(Objects.requireNonNull(map.get("experience")).toString());
                        }





                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(DoctorProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



        binding.buttonSaveDoctorProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> updateData = new HashMap<>();

                if (!binding.editDoctorName.getText().toString().isEmpty()){

                    updateData.put("name", binding.editDoctorName.getText().toString());
                }
                if (!binding.editDoctorEmail.getText().toString().isEmpty()){

                    updateData.put("email", binding.editDoctorEmail.getText().toString());
                }
                if (!binding.editDoctorSpeciality.getText().toString().isEmpty()){

                    updateData.put("specialist", binding.editDoctorSpeciality.getText().toString());
                }
                if (!binding.editDoctorWorkExperience.getText().toString().isEmpty()){

                    updateData.put("experience", binding.editDoctorWorkExperience.getText().toString());
                }

                database.getReference().child("Doctor").child(currentUser).setValue(updateData);

            }
        });




        }

    }

