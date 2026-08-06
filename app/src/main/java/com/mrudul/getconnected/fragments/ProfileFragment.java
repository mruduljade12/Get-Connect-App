package com.mrudul.getconnected.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrudul.getconnected.R;
import com.mrudul.getconnected.models.UserInfoModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    boolean isEditable = false;
    private static final String USER_NODE = "Users";
    UserInfoModel userModel;
    EditText name,email,address,password;
    AppCompatButton saveBtn,editBtn;


    // Firebase initialization
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        String id  = auth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USER_NODE).child(id);

        saveBtn = view.findViewById(R.id.saveBtn);
        editBtn = view.findViewById(R.id.editBtn);
        name = view.findViewById(R.id.pUserName);
        email = view.findViewById(R.id.pEmail);
        address = view.findViewById(R.id.pAddress);
        password = view.findViewById(R.id.pPassword);

        displayData();


        // set edit text disable
        name.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);
        password.setEnabled(false);

        editBtn.setOnClickListener(v->{
            if (isEditable){

                isEditTextEditable(false);
                isEditable = false;
                editBtn.setText("Edit");
            } else {
                isEditTextEditable(true);
                isEditable = true;
                editBtn.setText("Cancel");
            }
        });


        saveBtn.setOnClickListener(v->{

            String nameInput = name.getText().toString();
            String emailInput = email.getText().toString();
            String addressInput = address.getText().toString();
            String passwordInput = password.getText().toString();

            // username validation
            if (nameInput.isEmpty()) {
                name.setError("Username is required");
                name.requestFocus();
                return;
            }

            if (nameInput.length() < 3) {
                name.setError("Username must be at least 3 characters");
                name.requestFocus();
                return;
            }

            // Only allow letters, numbers, and underscores
            if (!nameInput.matches("^[a-zA-Z0-9_]+$")) {
                name.setError("Username can only contain letters, numbers, and underscores");
                name.requestFocus();
                return;
            }

            // email validation
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

            // password validation
            if (passwordInput.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            if (passwordInput.length() < 6) {
                password.setError("Password must be at least 6 characters long");
                password.requestFocus();
                return;
            }

            userModel = new UserInfoModel(nameInput,emailInput,addressInput,passwordInput);
            reference.setValue(userModel);

            displayData();

            isEditTextEditable(false);
            editBtn.setText("Edit");
        });
    }

    private void isEditTextEditable(boolean isEditable) {

        name.setEnabled(isEditable);
        email.setEnabled(isEditable);
        address.setEnabled(isEditable);
        password.setEnabled(isEditable);
    }

    private void displayData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    userModel = snapshot.getValue(UserInfoModel.class);
                    if (userModel != null){
                        name.setText(userModel.getUsername());
                        email.setText(userModel.getEmail());
                        address.setText(userModel.getAddress());
                        password.setText((userModel.getPassword()));
                    } else {
                        Toast.makeText(getContext(),"User not exist",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}