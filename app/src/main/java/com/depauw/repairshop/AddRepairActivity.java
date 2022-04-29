package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddRepairActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityAddRepairBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = DBHelper.getInstance(this);

        List<Vehicle> vehicles = helper.getAllVehicles();
//        List<String> vehicleStrings = new ArrayList<String>();
//
//        Iterator<Vehicle> vehicleListIterator = vehicles.iterator();
//
//        while(vehicleListIterator.hasNext())
//        {
//            vehicleStrings.add(vehicleListIterator.next().toString());
//        }
        ArrayAdapter<Vehicle> adapter = new ArrayAdapter<Vehicle>(this, android.R.layout.simple_spinner_item,vehicles);
        binding.spinnerVehicles.setAdapter(adapter);

        binding.buttonAddRepair.setOnClickListener(this);
        binding.edittextRepairDate.setOnClickListener(this);
    }

    private DatePickerDialog.OnDateSetListener datePickerDialog_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            String date = String.valueOf(month)+"/"+String.valueOf(dayOfMonth)+"/"+String.valueOf(year);
            binding.edittextRepairDate.setText(date);
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.edittext_repair_date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddRepairActivity.this, datePickerDialog_dateSetListener, LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                datePickerDialog.show();
                break;
            case R.id.button_add_repair:

                DBHelper helper = DBHelper.getInstance(this);
                Vehicle vehicle = (Vehicle)binding.spinnerVehicles.getSelectedItem();
                String date = binding.edittextRepairDate.getText().toString();
                double cost = Double.valueOf(binding.edittextRepairCost.getText().toString());
                String description = binding.edittextRepairDescription.getText().toString();
                Repair newRepair = new Repair(date, cost, description, vehicle.getVid());
                long result = helper.insertRepair(newRepair);
                if(result>=0)
                {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }

    }
}