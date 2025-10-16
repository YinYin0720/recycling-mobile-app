package com.example.e_cynic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.e_cynic.R;

public class ElectronicAppliancesExampleActivity extends AppCompatActivity
{
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electronic_appliances_example);

        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(ElectronicAppliancesExampleActivity.this,
                        RecycleActivity.class);
                startActivity(i);
            }
        });
    }
}