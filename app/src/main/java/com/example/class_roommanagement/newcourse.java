package com.example.class_roommanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class newcourse extends AppCompatActivity {
    public EditText courseName;
    public EditText enrollid;
    String username;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcourse);
        rootNode= FirebaseDatabase.getInstance();
        username=getIntent().getExtras().getString("user");
    }
    public void addnewcourse(View v)
    {
        courseName=(EditText)findViewById(R.id.editTextCourseName);
        enrollid=(EditText)findViewById(R.id.editTextSchoolName);
        String Course=courseName.getText().toString();
        String enroll=enrollid.getText().toString();
        CourseTaken c=new CourseTaken(Course,enroll);
        reference=rootNode.getReference("users");
        reference.child(username).child("course").child(c.name).setValue(c);
        reference=rootNode.getReference("users");
        reference.child(c.enrollid).setValue(c);
        Intent intent=new Intent(getApplicationContext(),addCourse.class);
        Bundle bundle=new Bundle();
        bundle.putString("unid","newcourse");
        bundle.putString("user",username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}