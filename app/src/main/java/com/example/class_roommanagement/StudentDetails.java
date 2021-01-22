package com.example.class_roommanagement;

public class StudentDetails {

    public String name,user,phone,password,profile;

    public StudentDetails(String name,String user,String phone,String password,String profile)
    {
        this.name=name;
        this.user=user;
        this.phone=phone;
        this.password=password;
        this.profile=profile;
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

}
