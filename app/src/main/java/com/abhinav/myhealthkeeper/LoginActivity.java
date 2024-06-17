package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhinav.myhealthkeeper.R;
import com.abhinav.myhealthkeeper.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    FirebaseAuth auth;

    String doctor = "Doctor";
    String patient = "Patient";
    static String role;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();


        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.progressbar.setVisibility(View.VISIBLE);

                String email= binding.username.getText().toString().trim();
                String password= binding.password.getText().toString().trim();

                if(email.isEmpty())
                {
                    binding.username.setError("Email is empty");
                    binding.username.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    binding.username.setError("Enter the valid email");
                    binding.username.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    binding.password.setError("Password is empty");
                    binding.password.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    binding.password.setError("Length of password is more than 6");
                    binding.password.requestFocus();
                    return;
                }


                auth.signInWithEmailAndPassword(binding.username.getText().toString(), binding.password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()){

                                    if (Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).isEmailVerified()) {

                                        String uId = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        database.getReference().child("User").child(uId).child("role")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        role = snapshot.getValue(String.class);
                                                        Toast.makeText(LoginActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();


                                                        assert role != null;
                                                        if (role.equals(patient)) {

                                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                            Toast.makeText(LoginActivity.this, role, Toast.LENGTH_SHORT).show();
                                                            Common.ROLE = "Patient";

                                                        } else if (role.equals(doctor)) {

                                                            startActivity(new Intent(LoginActivity.this, MainActivityDoctor.class));
                                                            Toast.makeText(LoginActivity.this, role, Toast.LENGTH_SHORT).show();
                                                            Common.ROLE = "Doctor";
                                                        }

                                                        binding.progressbar.setVisibility(View.GONE);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });

                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "Email is not verified", Toast.LENGTH_SHORT).show();
                                        binding.progressbar.setVisibility(View.GONE);
                                    }

                                }
                                else {
                                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



//        if (auth.getCurrentUser()!=null){
//
//            if (role.equals(patient)){
//
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            }
//
//            else {
//                startActivity(new Intent(LoginActivity.this, MainActivityDoctor.class));
//            }
//
//
//        }



        binding.newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterUser.class));
            }
        });



        // forget password

        binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.forget_dialog, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(LoginActivity.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

    }
}