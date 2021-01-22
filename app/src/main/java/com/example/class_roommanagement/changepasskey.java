package com.example.class_roommanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class changepasskey extends AppCompatActivity {

    EditText t;
    String s,userid;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepasskey);
        rootNode=FirebaseDatabase.getInstance();
        Bundle bundle = getIntent().getExtras();
        userid = bundle.getString("userid");
    }
    public void submit(View v)
    {
        t=(EditText) findViewById(R.id.editText_sendnewpass);
        s=t.getText().toString();
        rootNode=FirebaseDatabase.getInstance();
        reference=rootNode.getReference("users");
        reference.child(userid).child("password").setValue(s);

        Intent i=new Intent(getApplicationContext(),Main_Activity.class);
        startActivity(i);
    }
}