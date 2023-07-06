package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.foodFragment;
import com.example.myapplication.fragments.menuFragment;
import com.example.myapplication.fragments.personalFragment;
import com.example.myapplication.fragments.scannerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MealsDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        Intent onBordingIntent = new Intent(this, OnBordingScreen.class);
//        startActivity(onBordingIntent);


        dbHelper = new MealsDatabaseHelper(getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("LOGIN: " + SharedPreferenceManager.INSTANCE.getLogin(this));
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.b
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.menu) {
            selectedFragment = new menuFragment();
        } else if (itemId == R.id.personal) {
            selectedFragment = new personalFragment();
        } else if (itemId == R.id.scanner) {
            selectedFragment = new scannerFragment();
        }else if (itemId == R.id.food) {
            selectedFragment = new foodFragment();
        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };

}