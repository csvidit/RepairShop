package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityAddVehicleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.button_add_vehicle:

                int year = Integer.valueOf(binding.edittextYear.getText().toString());
                String makeAndModel = binding.edittextMakeModel.getText().toString();
                double purchasePrice = Double.valueOf(binding.edittextPrice.getText().toString());
                boolean isNew = binding.checkboxIsNew.isChecked();

                Vehicle newVehicle = new Vehicle(year, makeAndModel, purchasePrice, isNew);

                DBHelper helper = DBHelper.getInstance(this);

                long result = helper.insertVehicle(newVehicle);
                if(result>=0)
                {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }

    }
}