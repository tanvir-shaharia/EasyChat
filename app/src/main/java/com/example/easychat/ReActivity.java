package com.example.easychat;

import static com.example.easychat.utils.Valid.inputvaliedfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.easychat.databinding.ActivityReBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ReActivity extends AppCompatActivity{

    ActivityReBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();                              //Firebase Auth
        databaseReference= FirebaseDatabase.getInstance().getReference("user");       //DatabaseRef

        progressDialog=new ProgressDialog(ReActivity.this);
        progressDialog.setMessage("Please Wait..");

        binding.btn.setOnClickListener(view -> {
            String name     =binding.name.getText().toString();
            String email    =binding.email.getText().toString();
            String phone    =binding.phone.getText().toString();
            String password =binding.pass.getText().toString();
            String repassword =binding.repass.getText().toString();

            if (name.equals("")){
                inputvaliedfield(ReActivity.this,"Name feield can't be Empty");
            }else if (email.equals("")){
                inputvaliedfield(ReActivity.this,"Email feield can't be Empty");
            }else if (phone.equals("")){
                inputvaliedfield(ReActivity.this,"Phone feield can't be Empty");
            }else if (password.equals("")){
                inputvaliedfield(ReActivity.this,"Password feield can't be Empty");
            }else if (password.length()<6){
                inputvaliedfield(ReActivity.this,"Password must be 6 Charecters!");
            }
            else if (repassword.equals("")){
                inputvaliedfield(ReActivity.this,"Please fill re-type Password");
            }else {
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                            String user_id=firebaseUser.getUid();
                            long date=System.currentTimeMillis();

                            HashMap<String,Object> userData=new HashMap<>();

                            userData.put("name",name);
                            userData.put("email",email);
                            userData.put("phone",phone);
                            userData.put("password",password);
                            userData.put("user_id",user_id);
                            userData.put("usercreateAT",date);
                            userData.put("cover_pic","");
                            userData.put("profile_pic","");

                            databaseReference.child(user_id).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        startActivity(new Intent(ReActivity.this, MainActivity.class));
                                        finish();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    inputvaliedfield(ReActivity.this,e.getMessage().toString());
                                }
                            });


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        inputvaliedfield(ReActivity.this,e.getMessage().toString());
                    }
                });
            }



        });

        binding.alreadyText.setOnClickListener(view -> {
            startActivity(new Intent(ReActivity.this,LoginActivity.class));
            finish();
        });
    }
}