package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.depauw.repairshop.database.RepairWithVehicle;
import com.depauw.repairshop.database.Vehicle;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    List<RepairWithVehicle> repairs;
    Context context;

    public CustomAdapter(List<RepairWithVehicle> repairs, Context context) {
        this.repairs = repairs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return repairs.size();
    }

    @Override
    public Object getItem(int i) {
        return repairs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_results_row, parent, false);
        }

        TextView yearMakeModel = convertView.findViewById(R.id.text_year_make_model);
        TextView repairDate = convertView.findViewById(R.id.text_repair_date);
        TextView repairCost = convertView.findViewById(R.id.text_repair_cost);
        TextView repairDescription = convertView.findViewById(R.id.text_repair_description);

        RepairWithVehicle currRepair = (RepairWithVehicle) repairs.get(position);

        yearMakeModel.setText(currRepair.getVehicle().getMakeModel());
        repairDate.setText(currRepair.getRepair().getDate());
        repairCost.setText(String.valueOf(currRepair.getRepair().getCost()));
        repairDescription.setText(currRepair.getRepair().getDescription());

        return convertView;
    }
}
