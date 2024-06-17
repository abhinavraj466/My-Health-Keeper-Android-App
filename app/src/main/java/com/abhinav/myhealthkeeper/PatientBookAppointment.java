package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class PatientBookAppointment extends AppCompatActivity {

    String gender;

    EditText date , patientName, patientMobile, patientAddress,  patientAge;
    Button btnBookAppointment;
    RadioButton male , female;
    DatePickerDialog datePickerDialog;

    ProgressBar progressBar;

    FirebaseDatabase database;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PatientBookAppointment.this, FindDoctor.class));
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_book_appointment);


        database = FirebaseDatabase.getInstance();



        String doctorId = getIntent().getStringExtra("doctorId");
        String name = getIntent().getStringExtra("name");
        String specialist= getIntent().getStringExtra("specialist");




        date = (EditText) findViewById(R.id.patientChooseDate);
        patientName = (EditText) findViewById(R.id.patientName);
        patientAddress = (EditText) findViewById(R.id.patientAddress);
        patientAge = (EditText) findViewById(R.id.patientAge);
        patientMobile = (EditText) findViewById(R.id.patientMobileNumber);
        male= (RadioButton) findViewById(R.id.radioBtnMale);
        female= (RadioButton) findViewById(R.id.radioBtnFemale);
        btnBookAppointment = (Button) findViewById(R.id.btnBookAppointment);
        progressBar= findViewById(R.id.progressbar);





        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog = new DatePickerDialog(PatientBookAppointment.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        // set day of month , month and year value in the edit text
                        int monthOfYear = 0;
                        date.setText(dayOfMonth + "/"
                                + (month + 1) + "/" + year);
                    }
                }, mYear,mMonth,mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });


        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                getRadioBtn();

                if(patientName.getText().toString().isEmpty())
                {
                    patientName.setError("Patient name is empty");
                    patientName.requestFocus();
                    return;
                }
                if(patientAddress.getText().toString().isEmpty())
                {
                    patientAddress.setError("Address is empty");
                    patientAddress.requestFocus();
                    return;
                }

                if(patientMobile.getText().toString().isEmpty() || patientMobile.getText().toString().length()!=14)
                {
                    patientMobile.setError("Invalid mobile number");
                    patientMobile.requestFocus();
                    return;
                }

                if(patientAge.getText().toString().isEmpty())
                {
                    patientAge.setError("Age is empty");
                    patientAge.requestFocus();
                    return;
                }

                if(date.getText().toString().isEmpty())
                {
                    date.setError("Date is empty");
                    date.requestFocus();
                    return;
                }



                String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                HashMap<String , Object> patientDetails = new HashMap<String, Object>();
                patientDetails.put("name", patientName.getText().toString());
                patientDetails.put("mobile", patientMobile.getText().toString());
                patientDetails.put("address", patientAddress.getText().toString());
                patientDetails.put("age", patientAge.getText().toString());
                patientDetails.put("gender", gender);
                patientDetails.put("date", date.getText().toString());
                patientDetails.put("docId", doctorId);
                patientDetails.put("parentId", currentUser);

               String key =  database.getReference().child("DocAppointmentList").child(doctorId).push().getKey();

                database.getReference().child("DocAppointmentList").child(doctorId).child(key).setValue(patientDetails);


                HashMap<String , Object> doctorDetails = new HashMap<String, Object>();

                doctorDetails.put("name", name);
                doctorDetails.put("specialist", specialist);
                doctorDetails.put("date", date.getText().toString());
                doctorDetails.put("docId", doctorId);



                database.getReference().child("PatAppointmentList").child(currentUser).child(key).setValue(doctorDetails);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PatientBookAppointment.this, "Appointment Booked Successfully!!", Toast.LENGTH_SHORT).show();



                finish();

            }
        });



    }

    void getRadioBtn(){

        if (male.isChecked()){
            gender = "M";
        } else if (female.isChecked()) {
            gender = "F";
        }
        else {
            Toast.makeText(PatientBookAppointment.this, "Select Gender", Toast.LENGTH_SHORT).show();
        }

    }


}
