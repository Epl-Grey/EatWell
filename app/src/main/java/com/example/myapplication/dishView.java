package com.example.myapplication;

public class dishView {

    private String timeDay;
    private String name;
    private String time;

    private String id;

    // create constructor to set the values for all the parameters of the each single view
    public dishView(String timeDay, String name, String time, String id) {
        this.timeDay = timeDay;
        this.name = name;
        this.time = time;
        this.id = id;
    }


    public String getTimeDay() {
        return timeDay;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
