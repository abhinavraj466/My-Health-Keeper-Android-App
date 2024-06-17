package com.abhinav.myhealthkeeper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DoctorMyAppointmentAdapter extends RecyclerView.Adapter<DoctorMyAppointmentAdapter.ViewHolder>{


    ArrayList<ModalForDoctorMyAppointment> list;
    Context context;


    public DoctorMyAppointmentAdapter(ArrayList<ModalForDoctorMyAppointment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.doctor_my_appointment_rv_item, parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModalForDoctorMyAppointment modal = list.get(position);
        holder.patientName.setText(modal.getName());
        holder.time.setText(modal.getDate());



        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PatientDetails.class);
                intent.putExtra("name", modal.getName());
                intent.putExtra("receiverId", modal.getUserId());
                intent.putExtra("senderId", modal.getDocId());
                intent.putExtra("age", modal.getAge());
                intent.putExtra("date", modal.getDate());
                intent.putExtra("address", modal.getAddress());
                intent.putExtra("parentId", modal.getParentId());

                //String receiverId = modal.getUserId();
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

       TextView patientName, time;
       Button btnDetails;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = itemView.findViewById(R.id.patientName);
            time = itemView.findViewById(R.id.time);
            btnDetails = itemView.findViewById(R.id.btnDetails);


        }
    }
}
