package com.example.todoapp;

public class Days {
    private String dayid;
    private String dayname;

    public Days(){}
    public Days(String dayid, String dayname) {
        this.dayid = dayid;
        this.dayname = dayname;
    }

    public String getDayid() {
        return dayid;
    }

    public String getDayname() {
        return dayname;
    }
}
