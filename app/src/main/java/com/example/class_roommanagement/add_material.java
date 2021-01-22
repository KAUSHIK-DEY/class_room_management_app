package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class add_material extends AppCompatActivity {

    EditText tv;
    String s1,username,name,enrollid;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material2);
        Bundle b=getIntent().getExtras();
        name=b.getString("tag");
        enrollid=b.getString("tag1");
        username=b.getString("user");
        rootNode= FirebaseDatabase.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    public void selectfl(View v)
    {
        Intent choosefile=new Intent(Intent.ACTION_GET_CONTENT);
        choosefile.setType("*/*");
        startActivityForResult(choosefile,0);
    }
    @Override
    public void onActivityResult(int requestCode , int resultcode,Intent data)
    {
        super.onActivityResult(requestCode,resultcode,data);
        if(resultcode!=RESULT_OK)
        {
            return;
        }
        else
        {
            u=data.getData();
        }

    }
    public void addstudy(View v)
    {
        tv=(EditText) findViewById(R.id.editTextAssignmentName);
        s1=tv.getText().toString();
        String date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        reference=rootNode.getReference("users");
        Data db=new Data(s1,date);
        reference.child(username).child("course").child(name).child("material").child(s1).setValue(db);
        reference=rootNode.getReference("users");
        reference.child(enrollid).child("material").child(s1).setValue(db);


        if(u!=null)
        {
            StorageReference riversRef = storageReference.child(enrollid+"/"+"materials"+"/"+s1);
            riversRef.putFile(u)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            String notificationchannel="mychannel";
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),notificationchannel).
                                    setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Material Uploaded").
                                    setContentText(name+"/"+enrollid+"/"+s1).setContentInfo("info").setVibrate(new long[]{0,500,1000});

                            nm.notify(1,builder.build());
                            Intent i=new Intent(getApplicationContext(),activityevent.class);
                            Bundle  b=new Bundle();
                            b.putString("user",username);
                            b.putString("tag",name);
                            b.putString("tag1",enrollid);
                            b.putString("unid","add_material");
                            i.putExtras(b);
                            startActivity(i);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"something failed",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}