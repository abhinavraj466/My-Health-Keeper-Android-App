package com.abhinav.myhealthkeeper;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class MyAdapter extends FirebaseRecyclerAdapter<ModalForDoctor, MyAdapter.FindDoctorViewHolder>  {


    public MyAdapter(@NonNull FirebaseRecyclerOptions<ModalForDoctor> options) {
        super(options);

    }


    @Override
    protected void onBindViewHolder(@NonNull FindDoctorViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ModalForDoctor model) {

        holder.name.setText(model.getName());
        holder.specialist.setText(model.getSpecialist());

        holder.btnBook.setOnClickListener(v -> {

            AppCompatActivity appCompatActivity = (AppCompatActivity)v.getContext();
            Intent intent = new Intent(appCompatActivity , PatientBookAppointment.class);

            String doctorId= getRef(position).getKey();
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("name", model.getName());
            intent.putExtra("specialist" , model.getSpecialist());
            //intent.putExtra("parentId" , model.getSpecialist());

            appCompatActivity.startActivity(intent);

        });

    }

    @NonNull
    @Override
    public FindDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);

        return new FindDoctorViewHolder(view);
    }



    static class FindDoctorViewHolder extends RecyclerView.ViewHolder {

        TextView name , specialist;
        Button btnBook;


        public FindDoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.doctor_name);
            specialist = itemView.findViewById(R.id.specialist);
            btnBook = itemView.findViewById(R.id.btnBook);


        }
    }
}
