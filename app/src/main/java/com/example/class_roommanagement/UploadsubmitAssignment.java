package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadsubmitAssignment extends AppCompatActivity {

    EditText tb;
    String name1,name,enrollid,username,subname,date;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadsubmit_assignment);
        Bundle b=getIntent().getExtras();
        subname=b.getString("sub");
        name=b.getString("tag");
        date=b.getString("tag1");
        enrollid=b.getString("enroll");
        username=b.getString("user");
        rootNode= FirebaseDatabase.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
    }
    public void uploadstudent(View v)
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
    public void uploading(View v)
    {
        tb=(EditText) findViewById(R.id.editTextAssignmentName1);
        name1=tb.getText().toString();

        String date=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        reference=rootNode.getReference("users");
        Datadb db=new Datadb(username,name1,date);
        reference.child(username).child("course").child(name).child("assignment").setValue(db);
        reference=rootNode.getReference("users");
        reference.child(enrollid).child("submission").child(username).setValue(db);
        if(u!=null)
        {
            StorageReference riversRef = storageReference.child(enrollid+"/"+"assignment"+"/"+"submission"+"/"+username+"/"+name1);
            riversRef.putFile(u)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            String notificationchannel="mychannel";
                            NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),notificationchannel).
                                    setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Assignment Uploaded").
                                    setContentText(name+"/"+enrollid+"/"+name1).setContentInfo("info").setVibrate(new long[]{0,500,1000});

                            nm.notify(1,builder.build());;
                            Intent i=new Intent(getApplicationContext(),studentcourseinfo.class);
                            Bundle  b=new Bundle();
                            b.putString("user",username);
                            b.putString("tag",name);
                            b.putString("tag1",enrollid);
                            b.putString("unid","studentuploadassignment");
                            i.putExtras(b);
                            startActivity(i);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(getApplicationContext(),"something failed",Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }
    public void downloadassignment(View v) throws IOException {
        Log.d("date",date);
        Log.d("name",name);
        StorageReference riversRef = storageReference.child(enrollid+"/"+"assignment"+"/"+date+"/"+name+"/");
        Log.d("hello","w");

        File localFile = File.createTempFile(name,"pdf");

        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        String notificationchannel="mychannel";
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),notificationchannel).
                                setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setContentTitle("Assignment downloaded").
                                setContentText(name+"/"+enrollid+"/"+name1).setContentInfo("info").setVibrate(new long[]{0,500,1000});

                        nm.notify(1,builder.build());;

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
                Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}