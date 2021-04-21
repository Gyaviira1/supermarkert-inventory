package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPhone,mAddress;
    Button mRegistrationbtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore db;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullname=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.telphone);
        mAddress=findViewById(R.id.address);
        mRegistrationbtn=findViewById(R.id.registerbtn);
        mLoginBtn=findViewById(R.id.alreadyrigesterd);
        progressBar=findViewById(R.id.progressbar);

        fAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        //checking if the user is already loggedin and opening the main activity directly
//
//        if(fAuth.getCurrentUser() !=null){
//            startActivity(new Intent(getApplicationContext(),ListActivity.class));
//            finish();
//
//        }


        mRegistrationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullname=mFullname.getText().toString();
                String phone=mPhone.getText().toString().trim();
                String address=mAddress.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("password is required.");
                    return;
                }
                if(TextUtils.isEmpty(fullname)){
                    mFullname.setError("name is required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("phone number is required.");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    mAddress.setError("address is required.");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("password must have 6 or or characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Register user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           //send verification link
                            FirebaseUser fuser=fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"verification email has been sent",Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this,"verification link not sent"+e.getMessage(),Toast.LENGTH_LONG).show();
                                    Log.d("tag","onFailure: Email not reached"+e.getMessage());

                                }
                            });


                            Toast.makeText(Register.this,"user created",Toast.LENGTH_LONG).show();
                            userID=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=db.collection("Users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fName",fullname);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("address",address);

                            //inserting to database
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);

                                }
                            });

                          //  startActivity(new Intent(getApplicationContext(),ListActivity.class));
                            mFullname.setText("");
                            mAddress.setText("");
                            mEmail.setText("");
                            mPassword.setText("");
                            mPhone.setText("");

                        }else {
                            Toast.makeText(Register.this,"Error !"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
        });

    }
}