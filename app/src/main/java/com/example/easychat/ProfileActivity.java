package com.example.easychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.easychat.databinding.ActivityProfileBinding;
import com.example.easychat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity{

    ActivityProfileBinding binding;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            user_uid=firebaseUser.getUid();
        }

        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(user_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class);
                if (user != null){
                  if (user.getCover_pic() == null){
                      binding.coverPic.setImageResource(R.drawable.ic_logo);
                  }else {
                      Glide.with(ProfileActivity.this).load(user.getCover_pic()).into(binding.coverPic);
                  }
                   if (user.getProfile_pic() == null){
                        binding.profileImage.setImageResource(R.drawable.ic_logo);
                   }else {
                        Glide.with(ProfileActivity.this).load(user.getProfile_pic()).into(binding.profileImage);
                    }

                    binding.name.setText(user.getName());
                    binding.email.setText(user.getEmail());
                    binding.phone.setText(user.getPhone());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.edit.setOnClickListener(v -> {
           Intent intent= new Intent(ProfileActivity.this,EditProfileActivity.class);
           intent.putExtra("id",user_uid);
           startActivity(intent);
        });
    }
}