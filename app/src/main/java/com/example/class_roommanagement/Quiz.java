package com.example.class_roommanagement;

public class Quiz {
    String name,date,link;
    public Quiz(String name,String date,String link)
    {
     this.name=name;
     this.date=date;
     this.link=link;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }
}
