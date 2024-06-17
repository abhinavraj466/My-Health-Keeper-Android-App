package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.abhinav.myhealthkeeper.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindDoctor extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView  searchdoctor ;

    MyAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        searchdoctor = (SearchView) findViewById(R.id.searchdoctor);
        recyclerView = (RecyclerView) findViewById(R.id.doctor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<ModalForDoctor> options =
                new FirebaseRecyclerOptions.Builder<ModalForDoctor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor"), ModalForDoctor.class)
                        .build();

        adapter = new MyAdapter(options);
        recyclerView.setAdapter(adapter);








        // search operation


        searchdoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                processsearch(s);
                return false;
            }

            private void processsearch(String s) {

                FirebaseRecyclerOptions<ModalForDoctor> options =
                        new FirebaseRecyclerOptions.Builder<ModalForDoctor>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor")
                                        .orderByChild("name").startAt(s).endAt(s+"\uf8ff"), ModalForDoctor.class)
                                .build();

                adapter= new MyAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);
            }

        });



    }


    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}




