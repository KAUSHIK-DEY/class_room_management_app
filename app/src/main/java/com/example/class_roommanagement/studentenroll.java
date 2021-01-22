package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class studentenroll extends AppCompatActivity implements AdapterView.OnItemClickListener{

    Bundle bundle;
    String course,enroll,username;
    ArrayList<String> listitems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String[] values;
    ArrayList<CourseTaken> cr=new ArrayList<CourseTaken>();
    ListView list;
    Activity m;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentenroll);
        list=(ListView) findViewById(R.id.listview2);
        m=this;
        rootNode=FirebaseDatabase.getInstance();


        Intent intent=this.getIntent();
        if(intent!=null)
        {

            String  str=getIntent().getExtras().getString("unid");

            if(str.equals("main-activity"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                addtolist();
            }
            else if(str.equals("enrollcourse"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                addtolist();

            }

        }
        list.setOnItemClickListener(this);

    }
    public void addtolist()
    {
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkuser=reference.orderByChild("user").equalTo(username);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    int m1=0;
                    for(DataSnapshot snapshot1:snapshot.child(username).child("course").getChildren())
                    {

                        HashMap<String,CourseTaken> name1=(HashMap<String,CourseTaken>)snapshot1.getValue();
                        String s="";

                        CourseTaken z=new CourseTaken("a","b");
                        for(Map.Entry mapele:name1.entrySet())
                        {
                            String key=mapele.getKey().toString();
                            String value=mapele.getValue().toString();
                            if(key.equals("name"))
                            {
                                s=s+"COURSE NAME : "+value+" \n";
                                z.name=value;
                            }
                            else if(key.equals("enrollid"))
                            {

                                s=s+"ENROLL_ID : "+value;
                                z.enrollid=value;
                            }
                        }
                        cr.add(z);
                        listitems.add(s);

                    }
                    values=new String[listitems.size()];
                    for(int i=0;i<listitems.size();i++)
                    {
                        values[i]=listitems.get(i);
                    }
                    adapter=new ArrayAdapter<String>(m,R.layout.list_view,values);
                    list.setBackgroundColor(Color.WHITE);
                    list.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i=new Intent(getApplicationContext(),studentcourseinfo.class);
        Bundle  b=new Bundle();
        b.putString("tag",cr.get(position).name);
        b.putString("tag1",cr.get(position).enrollid);
        b.putString("user",username);
        b.putString("unid","studenttenroll");
        i.putExtras(b);
        startActivity(i);
    }

    public void enrollcourse(View v)
    {
        Intent i =new Intent(getApplicationContext(),enrollcourse.class);
        Bundle b=new Bundle();
        b.putString("user",username);
        i.putExtras(b);
        startActivity(i);

    }
}