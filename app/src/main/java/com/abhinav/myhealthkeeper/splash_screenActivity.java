package com.abhinav.myhealthkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.abhinav.myhealthkeeper.R;

public class splash_screenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginpage = new Intent(splash_screenActivity.this, LoginActivity.class);
                startActivity(loginpage);
                finish();
            }
        } ,4000);


    }
}