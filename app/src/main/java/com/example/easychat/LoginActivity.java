package com.example.easychat;

import static com.example.easychat.utils.Valid.inputvaliedfield;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.easychat.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();                              //Firebase Auth

        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Cheaking Info..");

        binding.btn.setOnClickListener(view -> {

            String email=binding.email.getText().toString();
            String password=binding.pass.getText().toString();

            if (email.equals("")){
                inputvaliedfield(LoginActivity.this,"Email feield can't be Empty");
            }else if (password.equals("")){
                inputvaliedfield(LoginActivity.this,"Password feield can't be Empty");
            }else if (password.length()<6){
                inputvaliedfield(LoginActivity.this,"Password must be 6 Charatcrs");
            }else {
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Sucessfully Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        inputvaliedfield(LoginActivity.this,e.getMessage().toString());

                    }
                });

            }
        });

        binding.dont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ReActivity.class));
                finish();
            }
        });
    }
}