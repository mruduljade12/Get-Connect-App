package com.mrudul.getconnected.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.mrudul.getconnected.R;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // declaration of variables
    TextView newUser,signUpWithPhone;
    EditText email,password;
    AppCompatButton loginBtn,googleBtn,facebookBtn;

    // declaration of firebase services
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialization of firebase services
        auth = FirebaseAuth.getInstance();

        // initialization of variables
        newUser = findViewById(R.id.newUser);
        signUpWithPhone = findViewById(R.id.signUpWithPhone);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        googleBtn = findViewById(R.id.googleLoginBtn);
        facebookBtn = findViewById(R.id.facebookLoginBtn);


        // Login Button Logic
        loginBtn.setOnClickListener(view -> {

            // retrieving text from edit text
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            // input validation
            if (emailInput.isEmpty()) {
                email.setError("Email is required");
                email.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                email.setError("Please enter a valid email address");
                email.requestFocus();
                return;
            }

            if (passwordInput.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            // prevent double taps during API call
            loginBtn.setEnabled(false);

            // authentication code
            auth.signInWithEmailAndPassword(emailInput,passwordInput)
                    .addOnCompleteListener(task -> {

                        // again enable
                        loginBtn.setEnabled(true);


                        if (task.isSuccessful()){

                            // User Account Created Successful
                            Intent intent = new Intent(Login.this, MainActivity.class);

                            // Clear task stack so user cannot go back to log in via back button
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);
                            finish();

                            Log.d(TAG,"User Get Login");
                        } else {

                            // User Login Unsuccessful
                            Toast.makeText(Login.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"User Unable To Login");
                        }
                    });

        });


        // navigate to register page
        newUser.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
            finish();
        });



        // navigate to signup with phone page
        signUpWithPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, SignUpWithPhoneNumber.class);
            startActivity(intent);
            finish();
        });
    }
}