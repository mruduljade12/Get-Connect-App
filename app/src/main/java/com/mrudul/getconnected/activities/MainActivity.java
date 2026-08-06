package com.mrudul.getconnected.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mrudul.getconnected.R;
import com.mrudul.getconnected.fragments.ChatFragment;
import com.mrudul.getconnected.fragments.ProfileFragment;
import com.mrudul.getconnected.fragments.StatusFragment;
import com.mrudul.getconnected.models.UserInfoModel;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView userImage;
    TextView userName;

    // Firebase Initialization
    FirebaseDatabase database;
    FirebaseAuth auth;
    private static final String USER_NODE = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userImage = findViewById(R.id.profileImageHeader);
        userName = findViewById(R.id.userName);

        // Firebase setup
        auth = FirebaseAuth.getInstance();
        String id = auth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(USER_NODE).child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    UserInfoModel user = snapshot.getValue(UserInfoModel.class);
                    if (user != null){
                        userName.setText(user.getUsername());
                    } else {
                        Toast.makeText(MainActivity.this,"User not Exist",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.chat){

                navigateFragment(new ChatFragment(),"Chat Fragment");
                return true;
            }

            if (menuItem.getItemId() == R.id.status){

                navigateFragment(new StatusFragment(),"Status Fragment");
                return true;
            }

            if (menuItem.getItemId() == R.id.profile){

                navigateFragment(new ProfileFragment(),"Profile Fragment");
                return true;
            }

            return true;
        });
    }

    private void navigateFragment(Fragment fragment,String name){

        // fragment navigation code
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView,fragment)
                .addToBackStack(name)
                .commit();
    }
}