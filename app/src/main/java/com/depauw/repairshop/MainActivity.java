package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonAddVehicle.setOnClickListener(this);
        binding.buttonAddRepair.setOnClickListener(this);
        binding.buttonSearchRepairs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_add_vehicle:
                Intent addVehicle = new Intent(this, AddVehicleActivity.class);
                startActivity(addVehicle);
                break;
            case R.id.button_add_repair:
                Intent addRepair = new Intent(this, AddRepairActivity.class);
                startActivity(addRepair);
                break;
            case R.id.button_search_repairs:
                Intent searchRepairs = new Intent(this, SearchRepairsActivity.class);
                startActivity(searchRepairs);
                break;
        }
    }
}