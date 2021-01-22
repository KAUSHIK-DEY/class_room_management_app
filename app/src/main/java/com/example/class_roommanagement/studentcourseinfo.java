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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class studentcourseinfo extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String name,enrollid,username;
    ListView list;
    Activity m;
    ArrayList<String> listitems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayList<Object> cr=new ArrayList<Object>();
    String[] values;
    Bundle bundle;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri u;
    String mh="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentcourseinfo);
        TextView tv=(TextView) findViewById(R.id.textView12);
        TextView tv1=(TextView) findViewById(R.id.textView17);
        list=(ListView) findViewById(R.id.listview2);
        m=this;
        rootNode=FirebaseDatabase.getInstance();
        Intent intent=this.getIntent();
        if(intent!=null) {

            String str = getIntent().getExtras().getString("unid");

            if (str.equals("studenttenroll")) {
                bundle = getIntent().getExtras();
                username = bundle.getString("user");
                name = bundle.getString("tag");
                enrollid = bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
                addtolist();
            } else if (str.equals("studentuploadassignment")) {
                bundle = getIntent().getExtras();
                username = bundle.getString("user");
                name = bundle.getString("tag");
                enrollid = bundle.getString("tag1");
                tv.setText(name);
                tv1.setText(enrollid);
                addtolist();

            }
            list.setOnItemClickListener(this);

        }
    }
    public void addtolist()
    {
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkuser=reference.orderByChild("enrollid").equalTo(enrollid);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1:snapshot.child(enrollid).child("quiz").getChildren())
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
                                s=s+"Name : "+value+" \n";
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
                    for (DataSnapshot snapshot1 : snapshot.child(enrollid).child("assignment").getChildren()) {

                        HashMap<String, Datadb> name1 = (HashMap<String, Datadb>) snapshot1.getValue();
                        Data x=new Data("a","b");

                        String s="";
                        for(Map.Entry mapele:name1.entrySet())
                        {
                            String key=mapele.getKey().toString();
                            String value=mapele.getValue().toString();
                            Log.d(value,"value");
                            if(key.equals("assname")) {
                                s = s + "Assignment name : " + value + "\n";
                                x.assname=value;
                            }
                            else if(key.equals("date")) {
                                s = s + "Submission Date : " + value + "\n";
                                x.date=value;

                            }
                            else
                                s=s+"Date Uploaded : "+value;


                        }
                        cr.add(x);
                        listitems.add(s);

                    }
                    for (DataSnapshot snapshot1 : snapshot.child(enrollid).child("material").getChildren()) {

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

        if(cr.get(position) instanceof Data) {
            Intent i=new Intent(getApplicationContext(),UploadsubmitAssignment.class);
            Bundle  b=new Bundle();
            b.putString("sub",name);
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
            Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(((Quiz) cr.get(position)).link));

            /*
            Bundle  b=new Bundle();
            b.putString("sub",name);
            b.putString("tag", ((Data) cr.get(position)).assname);
            b.putString("tag1", ((Data) cr.get(position)).date);
            b.putString("enroll",enrollid);
            b.putString("user", username);
            b.putString("data","Quiz");
            b.putString("unid", "addactivityevent");
            i.putExtras(b);*/
            startActivity(i);
        }
        else
        {
            Log.d("hello",cr.get(position).toString());
        }

    }

    }
