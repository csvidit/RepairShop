package com.depauw.repairshop.database;

public class Repair {

    private int rid;
    private String date;
    private double cost;
    private String description;
    private int vid;

    public Repair(String date, double cost, String description, int vid) {
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.vid = vid;
    }

    public Repair(int rid, String date, double cost, String description) {
        this.rid = rid;
        this.date = date;
        this.cost = cost;
        this.description = description;
    }

    public Repair(String date, double cost, String description) {
        this.date = date;
        this.cost = cost;
        this.description = description;
    }

    public int getRid() {
        return rid;
    }

    public String getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public int getVid() {
        return vid;
    }
}
