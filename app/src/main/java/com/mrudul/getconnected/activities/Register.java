package com.mrudul.getconnected.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    // declaration of variables
    TextView oldUser,signUpWithPhone;
    EditText username,email,password;
    AppCompatButton registerBtn,googleBtn,facebookBtn;

    // declaration of firebase services
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialization of firebase services
        auth = FirebaseAuth.getInstance();

        // initialization of variables
        oldUser = findViewById(R.id.oldUser);
        signUpWithPhone = findViewById(R.id.signUpWithPhone);
        username = findViewById(R.id.registerUserName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        registerBtn = findViewById(R.id.registerBtn);
        googleBtn = findViewById(R.id.googleLoginBtn);
        facebookBtn = findViewById(R.id.facebookLoginBtn);


        // Register Button Logic
        registerBtn.setOnClickListener(view -> {

            // retrieving text from edit text
            String usernameInput = username.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            auth.createUserWithEmailAndPassword(emailInput,passwordInput)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()){

                            // User Account Created Successful
                            Intent intent = new Intent(Register.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Log.d(TAG,"User Get Register");
                        } else {

                            // User Login Unsuccessful
                            Toast.makeText(Register.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"User Unable To Register");
                        }
                    });
        });



        // navigate to login page
        oldUser.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        });



        // navigate to signup with phone page
        signUpWithPhone.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, SignUpWithPhoneNumber.class);
            startActivity(intent);
            finish();
        });

    }
}