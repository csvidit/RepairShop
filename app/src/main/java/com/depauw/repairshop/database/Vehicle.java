package com.depauw.repairshop.database;

import androidx.annotation.NonNull;

public class Vehicle {

    private int vid;
    private int year;
    private String makeModel;
    private double purchasePrice;
    private boolean isNew;

    public Vehicle(int year, String make_model, double purchase_price, boolean is_new) {
        this.year = year;
        this.makeModel = make_model;
        this.purchasePrice = purchase_price;
        this.isNew = is_new;
    }

    public Vehicle(int vid, int year, String make_model, double purchase_price, boolean is_new) {
        this.vid = vid;
        this.year = year;
        this.makeModel = make_model;
        this.purchasePrice = purchase_price;
        this.isNew = is_new;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMakeModel() {
        return makeModel;
    }

    public void setMakeModel(String makeModel) {
        this.makeModel = makeModel;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        this.isNew = aNew;
    }

    @NonNull
    @Override
    public String toString()
    {
        String toString = year+" "+makeModel;
        return toString;
    }
}
