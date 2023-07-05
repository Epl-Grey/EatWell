package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class RegisterLifeActivity extends AppCompatActivity {

    RelativeLayout rl1;
    RelativeLayout rl2;
    RelativeLayout rl3;
    RelativeLayout rl4;

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_life);

        Intent intentNext = new Intent(this, RegisterActivity.class);

        rl1 = findViewById(R.id.rl1);
        rl2 = findViewById(R.id.rl2);
        rl3 = findViewById(R.id.rl3);
        rl4 = findViewById(R.id.rl4);
        btnNext = findViewById(R.id.btnNext);

        String aim = intentNext.getStringExtra("aim");
        String rost = intentNext.getStringExtra("rost");
        String weight = intentNext.getStringExtra("weight");
        String ages = intentNext.getStringExtra("ages");



        rl1.setOnClickListener(view -> {

            btnNext.setVisibility(View.VISIBLE);
        });

        rl2.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
        });

        rl3.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
        });

        rl4.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
        });

        btnNext.setOnClickListener(view -> {

        });

    }
}