package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorPrescribtion extends AppCompatActivity {

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;

    FirebaseDatabase database;

    ArrayList<PrescribtionModel> prescribtionModelList = new ArrayList<>();

    String doctorId, patientId, parentId, patName,docName,age,date;

    HashMap<String, String> details= new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescribtion);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

         doctorId = getIntent().getStringExtra("senderId");
         patientId = getIntent().getStringExtra("receiverId");
         parentId = getIntent().getStringExtra("parentId");
         patName = getIntent().getStringExtra("patientName");
         docName = getIntent().getStringExtra("doctorName");
         age= getIntent().getStringExtra("age");
         date = getIntent().getStringExtra("date");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               addView();
            }
        });


        details.put("docName", docName);
        details.put("patName", patName);
        details.put("date", date);
        details.put("docId", doctorId);
        details.put("patId", patientId);


        buttonSubmitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean b = checkIfValidAndRead();

                if (b){

                    saveDataToDatabase();

                    database.getReference().child("PatPrescList").child(parentId)
                                    .child(patientId).setValue(details);

                    database.getReference().child("DocPrescList").child(doctorId)
                            .child(patientId).setValue(details);

                    Toast.makeText(DoctorPrescribtion.this, "Prescription generated successfully ", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }



    private boolean checkIfValidAndRead() {

        prescribtionModelList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View prescriptionView = layoutList.getChildAt(i);

            EditText medicineName = (EditText)prescriptionView.findViewById(R.id.edit_medicine_name);

            PrescribtionModel model = new PrescribtionModel();

            if(!medicineName.getText().toString().equals("")){

                model.setMedicine(medicineName.getText().toString());

            }


            prescribtionModelList.add(model);

        }

        if(prescribtionModelList.size()==0){
            result = false;
            Toast.makeText(this, "Add Prescription First!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }




    private  void saveDataToDatabase(){



            database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference1 = database.getReference().child("DoctorPresc").child(doctorId).child(patientId);
            DatabaseReference databaseReference2 = database.getReference().child("PatientPresc").child(parentId).child(patientId);

            for(PrescribtionModel model : prescribtionModelList){

                String key = databaseReference1.push().getKey();
                assert key != null;
                databaseReference1.child(key).child("presc").setValue(model.getMedicine());
                databaseReference2.child(key).child("presc").setValue(model.getMedicine());


            }
    }



    private void addView() {

        @SuppressLint("InflateParams")
        final View prescriptionView = getLayoutInflater().inflate(R.layout.row_add_prescribtion,null,false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})

        EditText medicineName = (EditText)prescriptionView.findViewById(R.id.edit_medicine_name);
       // EditText dosage = (EditText) prescriptionView.findViewById(R.id.edit_dosage);
        ImageView imageClose = (ImageView)prescriptionView.findViewById(R.id.image_remove);


        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeView(prescriptionView);
            }
        });

        layoutList.addView(prescriptionView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
}