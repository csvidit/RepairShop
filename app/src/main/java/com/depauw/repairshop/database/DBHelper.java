package com.depauw.repairshop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="RepairShop.db";
    private static final int DB_VERSION=1;

    public static final String TABLE_VEHICLE="Vehicle";
    public static final String COL_VEHICLE_ID="vid";
    public static final String COL_YEAR="year";
    public static final String COL_MAKE_MODEL="make_model";
    public static final String COL_PURCHASE_PRICE="purchase_price";
    public static final String COL_IS_NEW="is_new";

    public static final String TABLE_REPAIR="Repair";
    public static final String COL_REPAIR_ID="rid";
    public static final String COL_DATE="date";
    public static final String COL_COST="cost";
    public static final String COL_DESCRIPTION="description";
    public static final String COL_FOREIGN_KEY_VEHICLE_ID="vehicle_id";

    public static DBHelper myInstance;


    private DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String vehicleSql = "CREATE TABLE "+ TABLE_VEHICLE+ " ("+
                COL_VEHICLE_ID+ " INTEGER NOT NULL,"+
                COL_YEAR+" INTEGER NOT NULL,"+
                COL_MAKE_MODEL+" TEXT NOT NULL,"+
                COL_PURCHASE_PRICE+" NUMERIC NOT NULL,"+
                COL_IS_NEW+ " INTEGER NOT NULL,"+
                "PRIMARY KEY(" + COL_VEHICLE_ID+ " AUTOINCREMENT)" +
                ")";

        String repairSql = "CREATE TABLE "+ TABLE_REPAIR+" ("+
                COL_REPAIR_ID+ " INTEGER NOT NULL,"+
                COL_DATE+" TEXT NOT NULL,"+
                COL_COST+" NUMERIC NOT NULL,"+
                COL_DESCRIPTION+" TEXT NOT NULL,"+
                COL_FOREIGN_KEY_VEHICLE_ID+" INTEGER NOT NULL,"+
                "PRIMARY KEY("+COL_REPAIR_ID+" AUTOINCREMENT),"+
                "FOREIGN KEY("+COL_FOREIGN_KEY_VEHICLE_ID+") REFERENCES "+TABLE_VEHICLE+"("+COL_VEHICLE_ID+")"+
                ")";

        db.execSQL(vehicleSql);
        db.execSQL(repairSql);
        Log.d("shawn", "Databases made");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static DBHelper getInstance(Context context)
    {
        if(myInstance == null)
        {
            myInstance = new DBHelper(context);
        }
        return myInstance;
    }

    public long insertVehicle(Vehicle vehicle)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_YEAR, vehicle.getYear());
        cv.put(COL_MAKE_MODEL, vehicle.getMakeModel());
        cv.put(COL_PURCHASE_PRICE, vehicle.getPurchasePrice());
        cv.put(COL_IS_NEW, vehicle.isNew());

        long result = db.insert(TABLE_VEHICLE, null, cv);
        db.close();

        Log.d("shawn", "Row added in "+TABLE_VEHICLE+" at "+ String.valueOf(result));

        return result;
    }

    public long insertRepair(Repair repair)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_DATE, repair.getDate());
        cv.put(COL_COST, repair.getCost());
        cv.put(COL_DESCRIPTION, repair.getDescription());
        cv.put(COL_FOREIGN_KEY_VEHICLE_ID, repair.getVid());

        long result = db.insert(TABLE_REPAIR, null, cv);
        db.close();

        Log.d("shawn", "Row added in "+TABLE_REPAIR+" at "+ String.valueOf(result));

        return result;
    }

    public List<RepairWithVehicle> getRepairsWithVehicle(String searchTerm) {
        SQLiteDatabase db = getReadableDatabase();
        String getRepairSql;
        if(searchTerm.equals(""))
        {
            getRepairSql = "SELECT * FROM " + TABLE_REPAIR;
        }
        else
        {
            getRepairSql = "SELECT * FROM " + TABLE_REPAIR + " WHERE " + COL_DESCRIPTION + " LIKE '%" + searchTerm + "%'";
        }
        Cursor repairCursor = db.rawQuery(getRepairSql, null);

        List<RepairWithVehicle> repairs = new ArrayList<RepairWithVehicle>();

        int idx_rid = repairCursor.getColumnIndex(COL_REPAIR_ID);
        int idx_date = repairCursor.getColumnIndex(COL_DATE);
        int idx_cost = repairCursor.getColumnIndex(COL_COST);
        int idx_description = repairCursor.getColumnIndex(COL_DESCRIPTION);
        int idx_vid = repairCursor.getColumnIndex(COL_FOREIGN_KEY_VEHICLE_ID);

        if (repairCursor.moveToFirst()) {
            do {

                int rid = repairCursor.getInt(idx_rid);
                String date = repairCursor.getString(idx_date);
                double cost = repairCursor.getDouble(idx_cost);
                String description = repairCursor.getString(idx_description);
                int vid = repairCursor.getInt(idx_vid);

                String getVehicleSql = "SELECT * FROM "+ TABLE_VEHICLE +" WHERE "+COL_VEHICLE_ID+" = " + vid;

                Cursor vehicleCursor = db.rawQuery(getVehicleSql, null);

                int idx_year = vehicleCursor.getColumnIndex(COL_YEAR);
                int idx_make_model = vehicleCursor.getColumnIndex(COL_MAKE_MODEL);
                int idx_purchase_price = vehicleCursor.getColumnIndex(COL_PURCHASE_PRICE);
                int idx_is_new = vehicleCursor.getColumnIndex(COL_IS_NEW);

                Vehicle thisVehicle=null;

                if(vehicleCursor.moveToFirst())
                {
                    int year = vehicleCursor.getInt(idx_year);
                    String make_model = vehicleCursor.getString(idx_make_model);
                    double purchase_price = vehicleCursor.getDouble(idx_purchase_price);
                    boolean is_new;
                    if(vehicleCursor.getInt(idx_is_new) == 1)
                    {
                        is_new = true;
                    }
                    else
                    {
                        is_new = false;
                    }
                    thisVehicle = new Vehicle(vid, year, make_model, purchase_price, is_new);
                }
                Repair thisRepair = new Repair(rid, date, cost, description);

                repairs.add(new RepairWithVehicle(thisRepair, thisVehicle));
            } while (repairCursor.moveToNext());
        }
        db.close();
        return repairs;
    }

    public List<Vehicle> getAllVehicles()
    {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_VEHICLE;
        Cursor cursor = db.rawQuery(sql, null);

        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        int idx_vid = cursor.getColumnIndex(COL_VEHICLE_ID);
        int idx_year = cursor.getColumnIndex(COL_YEAR);
        int idx_make_model = cursor.getColumnIndex(COL_MAKE_MODEL);
        int idx_purchase_price = cursor.getColumnIndex(COL_PURCHASE_PRICE);
        int idx_is_new = cursor.getColumnIndex(COL_IS_NEW);

        if(cursor.moveToFirst())
        {
            do {

                int vid = cursor.getInt(idx_vid);
                int year = cursor.getInt(idx_year);
                String make_model = cursor.getString(idx_make_model);
                double purchase_price = cursor.getDouble(idx_purchase_price);
                boolean is_new;
                if(cursor.getInt(idx_is_new) == 1)
                {
                    is_new = true;
                }
                else
                {
                    is_new = false;
                }
                vehicles.add(new Vehicle(vid, year, make_model, purchase_price, is_new));
            }while(cursor.moveToNext());
        }
        db.close();
        return vehicles;

    }

    public Vehicle getVehicleId(String vehicle)
    {
        SQLiteDatabase db = getReadableDatabase();

        String[] words = vehicle.split(" ");
        int query_year = Integer.valueOf(words[0]);
        String query_make_model = words[1]+" "+words[2];
        String sql = "SELECT * FROM " + TABLE_VEHICLE + " WHERE "
                + COL_MAKE_MODEL + "=" + query_make_model + " AND " + COL_YEAR + "=" + query_year;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            int idx_vid = cursor.getColumnIndex(COL_VEHICLE_ID);
            int idx_year = cursor.getColumnIndex(COL_YEAR);
            int idx_make_model = cursor.getColumnIndex(COL_MAKE_MODEL);
            int idx_purchase_price = cursor.getColumnIndex(COL_PURCHASE_PRICE);
            int idx_is_new = cursor.getColumnIndex(COL_IS_NEW);

            int vid = cursor.getInt(idx_vid);
            int year = cursor.getInt(idx_year);
            String make_model = cursor.getString(idx_make_model);
            double purchase_price = cursor.getDouble(idx_purchase_price);
            boolean is_new;
            if(cursor.getInt(idx_is_new) == 1)
            {
                is_new = true;
            }
            else
            {
                is_new = false;
            }

            return new Vehicle(vid, year, make_model, purchase_price, is_new);
        }

        return null;
    }
}
