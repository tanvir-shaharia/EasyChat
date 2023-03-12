package com.example.easychat;

import static com.example.easychat.utils.Valid.inputvaliedfield;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.easychat.databinding.ActivityEditProfileBinding;
import com.example.easychat.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity{

    ActivityEditProfileBinding binding;
    Intent intent;
    String user_uid;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    Uri coverpicUri, profilepicUri;
    String coverpicUrl, profilepic_Url;
    ProgressDialog progressDialog;
    String name;
    String email;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        if (intent.hasExtra("id")) {
            user_uid = intent.getStringExtra("id");
        }

        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setMessage("Please Wait..");


        storageReference = FirebaseStorage.getInstance().getReference("photo");


        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(user_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    if (user.getCover_pic() == null) {
                        binding.coverPic.setImageResource(R.drawable.ic_logo);
                    } else {
                        Glide.with(EditProfileActivity.this).load(user.getCover_pic()).into(binding.coverPic);
                    }
                    if (user.getProfile_pic() == null) {
                        binding.profileImage.setImageResource(R.drawable.ic_logo);
                    } else {
                        Glide.with(EditProfileActivity.this).load(user.getProfile_pic()).into(binding.profileImage);
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
        binding.coverPicEdit.setOnClickListener(v -> {

            ImagePicker.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start(12);

        });
        binding.profileImageEdit.setOnClickListener(v -> {

            ImagePicker.with(this)
                    .crop()                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start(13);

        });

        binding.btn.setOnClickListener(v -> {
            name = binding.name.getText().toString();
            email = binding.email.getText().toString();
            phone = binding.phone.getText().toString();
            if (name.equals("")) {
                inputvaliedfield(EditProfileActivity.this, "Name feield can't be Empty");
            } else if (email.equals("")) {
                inputvaliedfield(EditProfileActivity.this, "Email feield can't be Empty");
            } else if (phone.equals("")) {
                inputvaliedfield(EditProfileActivity.this, "Phone feield can't be Empty");
            } else {
                progressDialog.show();
                sendpic_storage();
            }
        });
    }

    private void sendpic_storage() {

        StorageReference storageReference2 = storageReference.child(user_uid).child("cover");

        storageReference2.putFile(coverpicUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            coverpicUrl = String.valueOf(uri);
                            sendpic_pro_storage();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                inputvaliedfield(EditProfileActivity.this, e.getMessage().toString());
            }
        });
    }
    private void sendpic_pro_storage() {
        StorageReference storageReference3 = storageReference.child(user_uid).child("profile");

        storageReference3.putFile(profilepicUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    storageReference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            profilepic_Url = String.valueOf(uri);
                            updateinfodb();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                inputvaliedfield(EditProfileActivity.this, e.getMessage().toString());
            }
        });


    }
    private void updateinfodb() {
        HashMap<String, Object> userData = new HashMap<>();

        userData.put("name", name);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("cover_pic", coverpicUrl);
        userData.put("profile_pic", profilepic_Url);

        databaseReference.child(user_uid).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                inputvaliedfield(EditProfileActivity.this, e.getMessage().toString());
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            coverpicUri = data.getData();
            binding.coverPic.setImageURI(coverpicUri);
        }
        if (requestCode == 13 && resultCode == RESULT_OK && data != null) {
            profilepicUri = data.getData();
            binding.profileImage.setImageURI(profilepicUri);
        }
    }

}