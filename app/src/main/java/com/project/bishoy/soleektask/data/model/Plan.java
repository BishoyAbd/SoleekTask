package com.project.bishoy.soleektask.data.model;

/**
 * Created by bisho on 1/27/2018.
 */

public class Plan {


    private int cost;
    private String location;
    private int attendees;

    public Plan(int cost, String location, int attendees) {
        this.cost = cost;
        this.location = location;
        this.attendees = attendees;
    }

    public Plan() {

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }
}
