package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activityevent extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String name,enrollid,username;
    ListView list;
    Activity m;
    ArrayList<String> listitems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    String[] values;
    ArrayList<Object> cr=new ArrayList<Object>();

    Bundle bundle;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri u;
    String mh="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityevent);
        TextView tv=(TextView) findViewById(R.id.textView5);
        TextView tv1=(TextView) findViewById(R.id.textView7);
        list=(ListView) findViewById(R.id.listview2);
        m=this;
        rootNode=FirebaseDatabase.getInstance();
        Intent intent=this.getIntent();
        if(intent!=null)
        {

            String  str=getIntent().getExtras().getString("unid");

            if(str.equals("addCourse"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                name=bundle.getString("tag");
                enrollid=bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
                addtolist();
            }
            else if(str.equals("add_material"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                name=bundle.getString("tag");
                enrollid=bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
                addtolist();

            }
            else if(str.equals("add_quiz"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                name=bundle.getString("tag");
                enrollid=bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
                addtolist();

            }
            else if(str.equals("activity_addassignment"))
            {
                bundle=getIntent().getExtras();
                username=bundle.getString("user");
                name=bundle.getString("tag");
                enrollid=bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
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
                    for(DataSnapshot snapshot1:snapshot.child(username).child("course").child(name).child("quiz").getChildren())
                    {

                        Quiz d=new Quiz("a","b","c");
                        HashMap<String,Quiz> name1=(HashMap<String,Quiz>)snapshot1.getValue();
                        String s="";
                        for(Map.Entry mapele:name1.entrySet())
                        {
                            String key=mapele.getKey().toString();
                            String value=mapele.getValue().toString();
                            if(key.equals("name"))
                            {
                                s=s+"Quiz Name : "+value+" \n";
                                d.name=value;

                            }
                            else if(key.equals("date"))
                            {

                                s=s+"Date : "+value+"\n";
                                d.date=value;
                            }
                            else if(key.equals("link"))
                            {
                                s=s+"Link : "+value;
                                d.link=value;
                            }
                        }
                        cr.add(d);

                        listitems.add(s);

                    }
                    for (DataSnapshot snapshot1 : snapshot.child(username).child("course").child(name).child("assignment").getChildren()) {

                        HashMap<String, Datadb> name1 = (HashMap<String, Datadb>) snapshot1.getValue();
                        String s="";
                        Data x=new Data("a","b");
                        for(Map.Entry mapele:name1.entrySet())
                        {
                            String key=mapele.getKey().toString();
                            String value=mapele.getValue().toString();
                            Log.d(value,"value");
                            if(key.equals("assname")) {
                                s = s + "Assignment name : " + value + "\n";
                                x.assname=value;
                            }
                            else if(key.equals("date"))
                            {
                                s=s+"Submission Date : "+value+"\n";
                                x.date=value;
                            }
                            else
                                s=s+"Date Uploaded : "+value;


                        }
                        cr.add(x);
                        listitems.add(s);

                    }
                    for (DataSnapshot snapshot1 : snapshot.child(username).child("course").child(name).child("material").getChildren()) {

                        HashMap<String, Data> name1 = (HashMap<String, Data>) snapshot1.getValue();
                        String s="";
                        for(Map.Entry mapele:name1.entrySet())
                        {
                            String key=mapele.getKey().toString();
                            String value=mapele.getValue().toString();
                            if(key.equals("assname"))
                            s=s+"Material name : "+value+"\n";
                            else
                            s=s+"Upload Date : "+value;
                        }
                        cr.add("material");
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
    public void gotoevent(View v)
    {
        Intent i=new Intent(getApplicationContext(),addtosubject.class);
        Bundle  b=new Bundle();
        b.putString("tag",name);
        b.putString("tag1",enrollid);
        b.putString("user",username);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(getApplicationContext(),ViewEvent.class);
        Bundle  b=new Bundle();
        if(cr.get(position) instanceof Data) {

            b.putString("tag", ((Data) cr.get(position)).assname);
            b.putString("tag1", ((Data) cr.get(position)).date);
            b.putString("enroll",enrollid);
            b.putString("user", username);
            b.putString("data","Data");
            b.putString("unid", "addactivityevent");
            i.putExtras(b);
            startActivity(i);
        }
        else if(cr.get(position) instanceof Quiz)
        {
            b.putString("tag", ((Data) cr.get(position)).assname);
            b.putString("tag1", ((Data) cr.get(position)).date);
            b.putString("enroll",enrollid);
            b.putString("user", username);
            b.putString("data","Quiz");
            b.putString("unid", "addactivityevent");
            i.putExtras(b);
            startActivity(i);
        }
    }
}