package com.example.class_roommanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){


            @Override
            public void run() {
                    Intent i=new Intent(MainActivity.this,Main_Activity.class);
                    MainActivity.this.startActivity(i);
                    MainActivity.this.finish();
            }
        },3000);
    }

}