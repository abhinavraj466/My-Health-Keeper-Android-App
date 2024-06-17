package com.abhinav.myhealthkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowPatientPrescriptionAdapter extends RecyclerView.Adapter<ShowPatientPrescriptionAdapter.ViewHolder> {


    Context context;
    ArrayList<ModalForPatientPrescription> medicineList;

    public ShowPatientPrescriptionAdapter(Context context, ArrayList<ModalForPatientPrescription> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.show_patient_prescription_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModalForPatientPrescription model = medicineList.get(position);

        holder.medName.setText(model.getPresc());
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }



    public static class  ViewHolder extends RecyclerView.ViewHolder{

        TextView medName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medName = itemView.findViewById(R.id.medName);
        }
    }
}
