package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.RepairWithVehicle;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.List;

public class SearchRepairsActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySearchRepairsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_repairs);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonFindRepairs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_find_repairs:
                DBHelper helper = DBHelper.getInstance(this);
                String searchTerm = binding.edittextSearchPhrase.getText().toString();
                List<RepairWithVehicle> repairs = helper.getRepairsWithVehicle(searchTerm);
                CustomAdapter adapter = new CustomAdapter(repairs, this);
                binding.listviewResults.setAdapter(adapter);
        }
    }
}