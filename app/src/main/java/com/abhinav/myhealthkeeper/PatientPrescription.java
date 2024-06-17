package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class PatientPrescription extends AppCompatActivity {

    RecyclerView recyclerView;

    LinearLayout layoutlist;

    ArrayList<ModalForPatientPrescription> medicineList = new ArrayList<>();

    ShowPatientPrescriptionAdapter adapter;

    FirebaseDatabase database;

    TextView doctorNameTxt, patientName, age, date, address;

    ImageView btnDownload;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_prescription);


        recyclerView = findViewById(R.id.patientPrescriptionListRecyclerView);
        doctorNameTxt = findViewById(R.id.doctorName_pat_pres);
        patientName = findViewById(R.id.patientName_pat_pres);
        age = findViewById(R.id.patientAge_pat_pres);
        date= findViewById(R.id.appointmentDate_pat_pres);
        address = findViewById(R.id.patientAddress_pat_pres);
        //btnDownload = findViewById(R.id.buttonDownload);
        layoutlist = findViewById(R.id.layout_list);



        database = FirebaseDatabase.getInstance();
        String currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String doctorId = getIntent().getStringExtra("doctorId");
        String  patientId = getIntent().getStringExtra("patientId");
        String  doctorName = getIntent().getStringExtra("doctorName");


        // For Patient Information

        database.getReference().child("DocAppointmentList").child(doctorId)
                .child(patientId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){

                            Map map = (Map) snapshot.getValue();

                            assert map != null;
                            patientName.setText(Objects.requireNonNull(map.get("name")).toString());
                            age.setText(Objects.requireNonNull(map.get("age")).toString());
                            address.setText(Objects.requireNonNull(map.get("address")).toString());
                            date.setText(Objects.requireNonNull(map.get("date")).toString());
                            doctorNameTxt.setText(doctorName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




        // Showing Medicine List

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShowPatientPrescriptionAdapter(this, medicineList);
        recyclerView.setAdapter(adapter);





        if (Common.ROLE.equals("Patient")) {
            database.getReference().child("PatientPresc").child(currentUser)
                    .child(patientId).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            medicineList.clear();

                            if (snapshot.exists()) {

                                for (DataSnapshot data : snapshot.getChildren()) {

                                    ModalForPatientPrescription modal = data.getValue(ModalForPatientPrescription.class);
                                    medicineList.add(modal);
                                }

                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(PatientPrescription.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else if (Common.ROLE.equals("Doctor")) {

            database.getReference().child("DoctorPresc").child(currentUser)
                    .child(patientId).addValueEventListener(new ValueEventListener() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            medicineList.clear();

                            if (snapshot.exists()) {

                                for (DataSnapshot data : snapshot.getChildren()) {

                                    ModalForPatientPrescription modal = data.getValue(ModalForPatientPrescription.class);
                                    medicineList.add(modal);

                                }

                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(PatientPrescription.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }


//        if (medicineList.size()!=0){
//
//            for(int i =0; i < medicineList.size(); i++){
//
//                //addView(modal.getPresc());
//
////            ModalForPatientPrescription modal = medicineList.get(i);
//                TextView textView1 = new TextView(this);
//                textView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//                textView1.setText(medicineList.get(i).getPresc());
//                //textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
//                //textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
//                layoutlist.addView(textView1);
//            }
//
//        }

//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                convertIntoPdf();
//            }
//        });


    }



    // Convert to pdf

    private void convertIntoPdf(){

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.activity_patient_prescription, null);
        DisplayMetrics displayMetrics = new DisplayMetrics();



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.getDisplay().getRealMetrics(displayMetrics);
            }
            else this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));



        PdfDocument document = new PdfDocument();

        int viewWidth = view.getMeasuredWidth();
        int viewHeight = view.getMeasuredHeight();

        view.layout(0,0,displayMetrics.widthPixels, displayMetrics.heightPixels);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(viewWidth, viewHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        //Canvas

        Canvas canvas = page.getCanvas();
        view.draw(canvas);

        // Finish the page

        document.finishPage(page);

        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName = "prescription.pdf";
        File file = new File(downloadDir, fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this, "Download Successfully!!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Log.d("mylog", "Error while writing" + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



//    private  void addView(String s){
//
//        @SuppressLint("InflateParams")
//        final View prescriptionView = getLayoutInflater().inflate(R.layout.show_patient_prescription_rv,null,false);
//
//        TextView medName = findViewById(R.id.medName);
//        medName.setText(s);
//
//        layoutlist.addView(prescriptionView);
//    }

}