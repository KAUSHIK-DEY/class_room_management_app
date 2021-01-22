package com.example.class_roommanagement;

public class TeacherDetails {

    public String name,user,phone,password,profile,course;

    public TeacherDetails(String name,String user,String phone,String password,String profile,String course)
    {
        this.name=name;
        this.user=user;
        this.phone=phone;
        this.password=password;
        this.profile=profile;
        this.course=course;

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile() {
        return profile;
    }

    public String getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public String getCourse() {
        return course;
    }
}
