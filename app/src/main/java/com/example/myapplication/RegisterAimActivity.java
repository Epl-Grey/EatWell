package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterAimActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_aim);
        Intent intentNext = new Intent(this, RegisterInformActivity.class);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnNext = findViewById(R.id.btnNext);

        btn1.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
            intentNext.putExtra("aim", "Потеря веса");
        });

        btn2.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
            intentNext.putExtra("aim", "Сохранение веса");
        });

        btn3.setOnClickListener(view -> {
            btnNext.setVisibility(View.VISIBLE);
            intentNext.putExtra("aim", "Набор мышечной массы ");
        });

        btnNext.setOnClickListener(view -> {
            startActivity(intentNext);
        });

    }
}