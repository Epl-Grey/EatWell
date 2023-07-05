package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterInformActivity extends AppCompatActivity {

    EditText editRost;
    EditText editWeight;
    EditText editAges;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_inform);

        Intent intentNext = new Intent(this, RegisterLifeActivity.class);

        editRost = findViewById(R.id.editRost);
        editWeight = findViewById(R.id.editWeight);
        editAges = findViewById(R.id.editAges);

        btnNext = findViewById(R.id.btnNext);

        String rost = editRost.getText().toString();
        String weight = editWeight.getText().toString();
        String ages = editAges.getText().toString();
        String aim = intentNext.getStringExtra("aim");
        btnNext.setOnClickListener(view -> {
            if (rost.matches("")) {
                if(weight.matches("")) {
                    if(ages.matches("")) {
                        intentNext.putExtra("rost", rost);
                        intentNext.putExtra("weight", weight);
                        intentNext.putExtra("ages", ages);
                        intentNext.putExtra("aim", aim);
                        startActivity(intentNext);
                    }
                }
            }
        });



    }
}