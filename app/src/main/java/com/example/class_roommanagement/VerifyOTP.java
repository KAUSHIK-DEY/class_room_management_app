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

public class VerifyOTP extends AppCompatActivity {

    String s,codebysystem,actualphone;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Activity m;
    PinView pv;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);
        rootNode = FirebaseDatabase.getInstance();
        bundle = getIntent().getExtras();
        s = bundle.getString("value");
        actualphone=bundle.getString("phone");
        Log.d("regd", s);
        Log.d("phone",actualphone);
        pv = (PinView) findViewById(R.id.pin_view1);
        m = this;
        sendverificationcodetouser(actualphone);
    }
    private void sendverificationcodetouser(String actualphone) {
        PhoneAuthProvider.verifyPhoneNumber(PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setActivity(m).setPhoneNumber(actualphone).setTimeout(60L, TimeUnit.SECONDS).setCallbacks(mcallbacks).build());
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s,forceResendingToken);
            codebysystem=s;
        }
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null)
            {
                Log.d("otp","auto");
                pv.setText(code);
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyOTP.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void verifycode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codebysystem,code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(VerifyOTP.this, (OnCompleteListener<AuthResult>) task -> {
            if (task.isSuccessful()) {
                Log.d("check","passed");
                Intent i=new Intent(getApplicationContext(),changepasskey.class);
                Bundle b=new Bundle();
                b.putString("userid",s);
                i.putExtras(b);
                startActivity(i);
            } else {
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.d("check","failed");
                    Toast.makeText(VerifyOTP.this, "Verification Not Completed Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void confirm(View v)
    {
       String s1=pv.getText().toString();
       if(!s1.isEmpty())
       {
           verifycode(s1);
       }
    }
}