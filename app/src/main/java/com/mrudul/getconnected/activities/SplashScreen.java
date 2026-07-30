package com.mrudul.getconnected.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.mrudul.getconnected.R;

public class SplashScreen extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = () -> {

            // slash screen change after 3 seconds
            if (auth.getCurrentUser() != null){

                // User is already login
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

                Log.d("User Already Login","User Login Successful");
            } else {

                // User is not Login
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

                Log.d("User Is Not Login","User Navigate To Login Page");
            }
        };

        handler.postDelayed(runnable,3000);
    }

}