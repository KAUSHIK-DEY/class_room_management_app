package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Activity_Login extends AppCompatActivity {

    EditText name,username,phoneno,password;
    String name1,user,phone,pass,profile,course;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    RadioGroup rd;
    RadioButton rd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        rootNode=FirebaseDatabase.getInstance();
    }
    public void checkuser(View v)
    {
        name=(EditText)findViewById(R.id.editText_Name);
        username=(EditText)findViewById(R.id.editText_Email);
        phoneno=(EditText)findViewById(R.id.editText_Phone);
        password=(EditText)findViewById(R.id.editText_password);
        user=username.getText().toString();
        pass=password.getText().toString();
        name1=name.getText().toString();
        phone=phoneno.getText().toString();
        rd=(RadioGroup) findViewById(R.id.rd1);
        int selectedid=rd.getCheckedRadioButtonId();
        rd2=(RadioButton) findViewById(selectedid);

        if(rd2.getText().toString().equals("teacher"))
            profile="teacher";
        else
            profile="student";

        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users");
        if(rd2.getText().toString().equals("teacher"))
        {
            TeacherDetails s=new TeacherDetails(name1,user,phone,pass,profile,"basic");
            reference.child(user).setValue(s);

        }
        else
        {
            StudentDetails s=new StudentDetails(name1,user,phone,pass,profile);
            reference.child(user).setValue(s);
        }

        Intent i=new Intent(getApplicationContext(),Main_Activity.class);
        startActivity(i);
    }

}