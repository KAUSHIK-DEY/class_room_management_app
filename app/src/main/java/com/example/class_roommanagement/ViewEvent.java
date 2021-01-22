package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewEvent extends AppCompatActivity {
    String username,name,enrollid,data;
    String assname,submitdate;
    FirebaseDatabase rootNode;
    ArrayList<String> listitems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Activity m;
    ArrayList<Object> cr=new ArrayList<Object>();
    String[] values;
    ListView list1;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        rootNode=FirebaseDatabase.getInstance();
        list1=(ListView) findViewById(R.id.listview3);
        Bundle b=getIntent().getExtras();
        m=this;
        name=b.getString("user");
        enrollid=b.getString("enroll");
        data=b.getString("data");
        Log.d("hello","world");
        if(data.equals("Data")) {
            assname = b.getString("tag");
            Log.d("hello","world");
            submitdate = b.getString("tag1");
            reference = FirebaseDatabase.getInstance().getReference("users");
            Query checkuser = reference.orderByChild("enrollid").equalTo(enrollid);
            checkuser.addListenerForSingleValueEvent(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                            for (DataSnapshot snapshot1 : snapshot.child("submission").getChildren()) {

                                HashMap<String, Datadb> name1 = (HashMap<String, Datadb>) snapshot1.getValue();
                                Data x = new Data("a", "b");

                                String s = "";
                                for (Map.Entry mapele : name1.entrySet()) {
                                    String key = mapele.getKey().toString();
                                    String value = mapele.getValue().toString();
                                    Log.d(value, "value");
                                    if (key.equals("assname")) {
                                        s = s + "RegdNO : " + value + "\n";
                                        x.assname = value;
                                    } else if (key.equals("date")) {
                                        s = s + "Submission Date : " + value + "\n";
                                        x.date = value;

                                    } else
                                        s = s + "Date Uploaded : " + value;


                                }
                                cr.add(x);
                                listitems.add(s);


                            }

                        }
                        values = new String[listitems.size()];
                        for (int i = 0; i < listitems.size(); i++) {
                            values[i] = listitems.get(i);
                        }
                        adapter = new ArrayAdapter<String>(m, R.layout.list_view, values);
                        list1.setBackgroundColor(Color.WHITE);
                        list1.setAdapter(adapter);
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(data.equals("Quiz"))
        {

        }
    }
}