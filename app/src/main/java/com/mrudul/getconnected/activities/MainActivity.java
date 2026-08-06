package com.mrudul.getconnected.activities;

import android.os.Bundle;
import android.view.MenuItem;

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
import com.mrudul.getconnected.R;
import com.mrudul.getconnected.fragments.ChatFragment;
import com.mrudul.getconnected.fragments.ProfileFragment;
import com.mrudul.getconnected.fragments.StatusFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

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