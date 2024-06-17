package com.abhinav.myhealthkeeper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class PatientMyAppointmentAdapter extends RecyclerView.Adapter<PatientMyAppointmentAdapter.ViewHolder>{

    ArrayList<ModalForPatientMyAppointment> list;
    Context context;

    public PatientMyAppointmentAdapter(ArrayList<ModalForPatientMyAppointment> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.patient_my_appointment_rv, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModalForPatientMyAppointment model = list.get(position);
        holder.doctorName.setText(model.getName());
        holder.specialist.setText(model.getSpecialist());
        holder.appointmentDate.setText(model.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context , Chat.class);


                intent.putExtra("senderId", model.getUserId());
                intent.putExtra("receiverId", model.getDocId());
                intent.putExtra("name", model.getName());

                context.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {

        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView appointmentDate, specialist, doctorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName = itemView.findViewById(R.id.doctorName);
            specialist = itemView.findViewById(R.id.specialist);
            appointmentDate = itemView.findViewById(R.id.appointmentDate);

        }
    }

}
