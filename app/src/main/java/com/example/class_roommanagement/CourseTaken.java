package com.example.class_roommanagement;

public class CourseTaken {
    String name,enrollid;
    public CourseTaken(String name,String enrollid)
    {
        this.name=name;
        this.enrollid=enrollid;
    }

    public String getName() {
        return name;
    }

    public String getEnrollid() {
        return enrollid;
    }
}
