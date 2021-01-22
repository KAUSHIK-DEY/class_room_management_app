package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main_Activity extends AppCompatActivity {

    EditText name,pass;
    String sname,spass;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rootNode=FirebaseDatabase.getInstance();

    }
    public void gotoLogin(View v)
    {
        Intent i=new Intent(getApplicationContext(),Activity_Login.class);
        startActivity(i);
    }
    public void gotoforgot(View v)
    {
        Intent i=new Intent(getApplicationContext(),password_reset.class);
        startActivity(i);
    }
    public void registerStudent(View v)
    {
        name=(EditText) findViewById(R.id.editText_EmailSignUp);
        pass=(EditText) findViewById(R.id.editText_PasswordSignUp);
        sname=name.getText().toString();
        spass=pass.getText().toString();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkuser=reference.orderByChild("user").equalTo(sname);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String passwordfromDb=dataSnapshot.child(sname).child("password").getValue(String.class);
                    if(passwordfromDb.equals(spass))
                    {
                        String profile=dataSnapshot.child(sname).child("profile").getValue(String.class);
                        if(profile.equals("teacher")) {
                            Intent i = new Intent(getApplicationContext(), addCourse.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("unid", "main-activity");
                            bundle.putString("user", sname);
                            i.putExtras(bundle);
                            startActivity(i);
                        }else
                        {
                            Intent i = new Intent(getApplicationContext(), studentenroll.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("unid", "main-activity");
                            bundle.putString("user", sname);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }
                    else
                    {
                        Toast.makeText(Main_Activity.this,"ENTER VALID PASSWORD",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(),Activity_Login.class);
                    startActivity(i);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {




            }
        });



    }

}