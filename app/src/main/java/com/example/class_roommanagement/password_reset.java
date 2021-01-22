package com.example.class_roommanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class password_reset extends AppCompatActivity {

    EditText us;
    String s,actualphone;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Intent i;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        rootNode=FirebaseDatabase.getInstance();

    }
   public void sendotp(View v)
   {
       us=(EditText) findViewById(R.id.editText_sendotpsign);
       s=us.getText().toString();
       DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
       Query checkuser=reference.orderByChild("user").equalTo(s);
       checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists())
               {
                   Log.d("welcome","message");
                   String phonefromdb=dataSnapshot.child(s).child("phone").getValue(String.class);
                   actualphone="+91"+phonefromdb;
                   i=new Intent(getApplicationContext(),VerifyOTP.class);
                   bundle=new Bundle();
                   bundle.putString("value",s);
                   bundle.putString("phone",actualphone);
                   i.putExtras(bundle);
                   Log.d("kaushik",s);
                   startActivity(i);
               }
               else
               {
                  Toast.makeText(getApplicationContext(),"No Such Phone Exists",Toast.LENGTH_LONG).show();
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {




           }
       });


   }
}