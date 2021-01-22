package com.example.class_roommanagement;

public class Datadb {
    String assname,date,dateuploaded;
    public Datadb(String name,String date,String dateuploaded)
    {
        this.assname=name;
        this.date=date;
        this.dateuploaded=dateuploaded;
    }

    public java.lang.String getdate() {
        return date;
    }
    public String getassname()
    {
        return assname;
    }

    public String getDateuploaded() {
        return dateuploaded;
    }
}
