package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class enrollcourse extends AppCompatActivity {

    public EditText enrollid;
    String username,Course;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollcourse);
        rootNode= FirebaseDatabase.getInstance();
        username=getIntent().getExtras().getString("user");
    }
    public void enrollinto(View v)
    {
        enrollid=(EditText)findViewById(R.id.editTextSchoolName1);
        String enroll=enrollid.getText().toString();
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkuser=reference.orderByChild("enrollid").equalTo(enroll);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                                     @Override
                                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                         if (snapshot.exists()) {
                                                             int m1 = 0;
                                                             for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                                 HashMap<String,CourseTaken> name1=(HashMap<String,CourseTaken>)snapshot1.getValue();
                                                                 String s="";

                                                                 CourseTaken z=new CourseTaken("a","b");
                                                                 for(Map.Entry mapele:name1.entrySet())
                                                                 {
                                                                     String key=mapele.getKey().toString();
                                                                     String value=mapele.getValue().toString();
                                                                     if(key.equals("name"))
                                                                     {
                                                                        Course=value;
                                                                         CourseTaken c=new CourseTaken(Course,enroll);
                                                                         reference=rootNode.getReference("users");
                                                                         reference.child(username).child("course").child(c.name).setValue(c);
                                                                         Intent intent=new Intent(getApplicationContext(),studentenroll.class);
                                                                         Bundle bundle=new Bundle();
                                                                         bundle.putString("unid","enrollcourse");
                                                                         bundle.putString("enrollid",enroll);
                                                                         bundle.putString("user",username);
                                                                         intent.putExtras(bundle);
                                                                         startActivity(intent);
                                                                     }
                                                                 }
                                                             }
                                                         }
                                                     }
                                                     @Override
                                                     public void onCancelled(@NonNull DatabaseError error) {

                                                     }
                                                 });


    }
}