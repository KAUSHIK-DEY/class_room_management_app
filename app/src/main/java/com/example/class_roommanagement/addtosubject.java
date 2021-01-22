package com.example.class_roommanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class addtosubject extends AppCompatActivity {

    RadioGroup rd;
    RadioButton rd2;
    String profile,username,name,enrollid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtosubject);
        Bundle b=getIntent().getExtras();
        name=b.getString("tag");
        enrollid=b.getString("tag1");
        username=b.getString("user");

    }
    public void movetonext(View v)
    {
        rd=(RadioGroup) findViewById(R.id.radioEventGroup);
        int selectedid=rd.getCheckedRadioButtonId();
        rd2=(RadioButton) findViewById(selectedid);
        if(rd2.getText().toString().equals("Add Assignment")) {
            profile = "Add Assignment";
            Intent i=new Intent(getApplicationContext(),activity_addassignment.class);
            Bundle b=new Bundle();
            b.putString("tag",name);
            b.putString("tag1",enrollid);
            b.putString("user",username);
            i.putExtras(b);
            startActivity(i);
        }
        else if(rd2.getText().toString().equals("Add Material")) {
            profile = "Add Material";
            Intent i=new Intent(getApplicationContext(),add_material.class);
            Bundle b=new Bundle();
            b.putString("tag",name);
            b.putString("tag1",enrollid);
            b.putString("user",username);
            i.putExtras(b);
            startActivity(i);
        }
        else {
            profile = "ADD Quiz";
            Intent i=new Intent(getApplicationContext(),add_quiz.class);
            Bundle b=new Bundle();
            b.putString("tag",name);
            b.putString("tag1",enrollid);
            b.putString("user",username);
            i.putExtras(b);
            startActivity(i);
        }
    }
}