package com.abhinav.myhealthkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abhinav.myhealthkeeper.databinding.ActivityRegisterUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    ActivityRegisterUserBinding binding;

    private FirebaseAuth auth;
    FirebaseDatabase database;

    String role;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRadioBtn();

                String email= binding.enterEmail.getText().toString().trim();
                String password= binding.password.getText().toString().trim();
                String confirmPassword = binding.confirmPassword.getText().toString().trim();

                if(email.isEmpty())
                {
                    binding.enterEmail.setError("Email is empty");
                    binding.enterEmail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    binding.enterEmail.setError("Enter the valid email");
                    binding.enterEmail.requestFocus();
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
                    binding.password.setError("Length of password should be minimum 6");
                    binding.password.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)){

                    binding.confirmPassword.setError("Password not matched");
                    return  ;
                 }


                auth.createUserWithEmailAndPassword
                                (binding.enterEmail.getText().toString(), binding.password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                    assert firebaseUser != null;
                                    firebaseUser.sendEmailVerification();


                                    ModalForSignup user = new ModalForSignup
                                            (binding.enterEmail.getText().toString(), binding.name.getText().toString(), binding.password.getText().toString(), role);

                                    String id = Objects.requireNonNull(task.getResult().getUser()).getUid();

                                    database.getReference().child("User").child(id).setValue(user);

                                    if (role.equals("Doctor")) {

                                        HashMap<String , Object> doctor = new HashMap<String, Object>();
                                        doctor.put("name", binding.name.getText().toString());
                                        doctor.put("email", binding.enterEmail.getText().toString());

                                        database.getReference().child("Doctor").child(id).setValue(doctor);


                                    }



                                    Toast.makeText(RegisterUser.this, "Account created successfully , check email to verify", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(RegisterUser.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
        });


        binding.btnLoginForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this, LoginActivity.class));
            }
        });

    }

    void getRadioBtn(){

        if (binding.radioBtnDoctor.isChecked()){
            role = "Doctor";
        } else if (binding.radioBtnPatient.isChecked()) {
            role = "Patient";
        }
        else {
            Toast.makeText(RegisterUser.this, "Select between patient or doctor", Toast.LENGTH_SHORT).show();
        }

    }


        // get value from radio button




        //====================================================================================

//        emailEdttxt = (EditText) findViewById(R.id.enter_email);
//        nameEdttext = (EditText) findViewById(R.id.name);
//        passwordEdttext = (EditText) findViewById(R.id.password);
//        confirm_passwordEdttext = (EditText) findViewById(R.id.confirm_password);
//
//        login = (TextView) findViewById(R.id.btnLogin_form);
//        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
//
//        progressBar = (ProgressBar) findViewById(R.id.progressbar);
//
//        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                createAccount();
//
//            }
//        });
//
//
//
//    }
//
//    void createAccount(){
//
//        String email = emailEdttxt.getText().toString();
//        String password = passwordEdttext.getText().toString();
//        String name = nameEdttext.getText().toString();
//        String confirm_password = confirm_passwordEdttext.getText().toString();
//
//        boolean isValidated = validateData(email, password, confirm_password);
//
//        if (!isValidated){
//            return;
//        }
//
//        createAccountInFirebase(email, password);
//
//    }
//
//    boolean validateData(String email , String password, String confirm_password){
//
//        // validate data input by user
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            emailEdttxt.setError("Email is Invalid !");
//            return  false;
//        }
//
//        if (password.length()<= 6){
//
//            passwordEdttext.setError("Password length must be greater than 6");
//            return  false;
//        }
//
//        if (!password.equals(confirm_password)){
//
//            confirm_passwordEdttext.setError("Password not matched");
//            return  false;
//        }
//
//        return true;
//
//    }
//
//    void  createAccountInFirebase(String email , String password){
//
//        changeInProgress(true);
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterUser.this,
//                new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//
//                    // Creating a/c is done
//
//                    Toast.makeText(RegisterUser.this, "Account created successfully , check email to verify", Toast.LENGTH_SHORT).show();
//                    firebaseAuth.getCurrentUser().sendEmailVerification();
//                    firebaseAuth.signOut();
//                    finish();
//                }
//                else {
//                    // failure
//
//                    Toast.makeText(RegisterUser.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//
//
//    void changeInProgress(boolean inProgress){
//
//        if (inProgress){
//            progressBar.setVisibility(View.VISIBLE);
//            btnCreateAccount.setVisibility(View.GONE);
//        }
//        else {
//            progressBar.setVisibility(View.GONE);
//            btnCreateAccount.setVisibility(View.VISIBLE);
//        }
//
//    }
//



}