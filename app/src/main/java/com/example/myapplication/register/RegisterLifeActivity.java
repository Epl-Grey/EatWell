package com.example.myapplication.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;

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


        TextView login = findViewById(R.id.login);
        String aim = getIntent().getStringExtra("aim");
        intentNext.putExtra("aim", aim);

        String rost = getIntent().getStringExtra("rost");
        intentNext.putExtra("rost", rost);

        String weight = getIntent().getStringExtra("weight");
        intentNext.putExtra("weight", weight);

        String ages = getIntent().getStringExtra("ages");
        intentNext.putExtra("ages", ages);

        String sex = getIntent().getStringExtra("sex");
        intentNext.putExtra("sex", sex);


        rl1.setOnClickListener(view -> {
            intentNext.putExtra("activity", "Сидячий");
            btnNext.setVisibility(View.VISIBLE);
        });

        rl2.setOnClickListener(view -> {
            intentNext.putExtra("activity", "Малоактивный");
            btnNext.setVisibility(View.VISIBLE);
        });

        rl3.setOnClickListener(view -> {
            intentNext.putExtra("activity", "Активный");
            btnNext.setVisibility(View.VISIBLE);
        });

        rl4.setOnClickListener(view -> {
            intentNext.putExtra("activity", "Очень активный");
            btnNext.setVisibility(View.VISIBLE);
        });

        btnNext.setOnClickListener(view -> {

            startActivity(intentNext);
        });

    }
}