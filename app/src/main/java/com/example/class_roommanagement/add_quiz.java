package com.example.class_roommanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_quiz extends AppCompatActivity {
    EditText tv,tv1,tv2;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String s1,s2,s3,username,name,enrollid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        Bundle b=getIntent().getExtras();
        name=b.getString("tag");
        enrollid=b.getString("tag1");
        username=b.getString("user");
        rootNode= FirebaseDatabase.getInstance();
    }
    public void upload(View v)
    {
        tv=(EditText) findViewById(R.id.editTextQuizName);
        tv1=(EditText) findViewById(R.id.editTextQuizDate);
        tv2=(EditText) findViewById(R.id.editTextQuizLink);
        s1=tv.getText().toString();
        s2=tv1.getText().toString();
        s3=tv2.getText().toString();
        Quiz q=new Quiz(s1,s2,s3);
        String date=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSSSSS", Locale.getDefault()).format(new Date());
        reference=rootNode.getReference("users");
        reference.child(username).child("course").child(name).child("quiz").child(date).setValue(q);
        reference=rootNode.getReference("users");
        reference.child(enrollid).child("quiz").child(date).setValue(q);
        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String notificationchannel="mychannel";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),notificationchannel).
                setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Quiz Uploaded").
                setContentText(name+"/"+enrollid+"/"+s1).setContentInfo("info").setVibrate(new long[]{0,500,1000});

        nm.notify(1,builder.build());
        Intent i=new Intent(getApplicationContext(),activityevent.class);
        Bundle  b=new Bundle();
        b.putString("user",username);
        b.putString("tag",name);
        b.putString("tag1",enrollid);
        b.putString("unid","add_quiz");
        i.putExtras(b);
        startActivity(i);

    }
}