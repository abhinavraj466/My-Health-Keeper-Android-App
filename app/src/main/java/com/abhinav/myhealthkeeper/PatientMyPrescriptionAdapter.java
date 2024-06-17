package com.abhinav.myhealthkeeper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientMyPrescriptionAdapter extends RecyclerView.Adapter<PatientMyPrescriptionAdapter.ViewHolder>  {

    Context context;
    ArrayList<ModelForMyPrescription> modelForMyPrescriptionArrayList;

    public PatientMyPrescriptionAdapter(Context context, ArrayList<ModelForMyPrescription> modelForMyPrescriptionArrayList) {
        this.context = context;
        this.modelForMyPrescriptionArrayList = modelForMyPrescriptionArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_prescription_rv, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelForMyPrescription modal = modelForMyPrescriptionArrayList.get(position);
        holder.doctorName.setText(modal.getDocName());
        holder.appointmentDate.setText(modal.getDate());

        holder.prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PatientPrescription.class);
                intent.putExtra("patientId",modal.getPatId());
                intent.putExtra("doctorId",modal.getDocId());
                intent.putExtra("doctorName",modal.getDocName());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return modelForMyPrescriptionArrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName, appointmentDate, prescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName = itemView.findViewById(R.id.doctorName_my_presc);
            appointmentDate = itemView.findViewById(R.id.appointmentDate_my_presc);
            prescription = itemView.findViewById(R.id.prescription_my_presc);
        }
    }
}
