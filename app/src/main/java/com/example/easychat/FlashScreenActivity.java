package com.example.easychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FlashScreenActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();

            }
        },2000);

    }

    private void nextActivity() {

        if (firebaseUser!=null){
            startActivity(new Intent(FlashScreenActivity.this,MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(FlashScreenActivity.this, LoginActivity.class));
            finish();
        }
    }
}