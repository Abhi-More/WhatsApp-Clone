package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Setting_Activity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    ImageButton ib_upload_img;
    CircleImageView img_profile;
    Button btn_save;
    ProgressBar progressBar;
    EditText et_username, et_description;
    User user;
    LinearLayout ll_logout, ll_del_account, ll_about_us;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        ib_upload_img = findViewById(R.id.ib_upload_img);
        img_profile = findViewById(R.id.img_profile);
        btn_save = findViewById(R.id.btn_save);
        progressBar = findViewById(R.id.progressBar);
        et_username = findViewById(R.id.et_username);
        et_description = findViewById(R.id.et_description);

        progressBar.setVisibility(View.INVISIBLE);
        btn_save.setVisibility(View.VISIBLE);

        ll_logout = findViewById(R.id.ll_logout);
        ll_about_us = findViewById(R.id.ll_about_us);
        ll_del_account = findViewById(R.id.ll_del_account);

        findViewById(R.id.ib_back_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting_Activity.this, MainActivity.class));
                finishAffinity();
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user = snapshot.getValue(User.class);
                                Picasso.get()
                                        .load(user.getProfilePic()).placeholder(R.drawable.whatsapp_avatar)
                                        .into(img_profile);
                                et_username.setText(user.getUserName());
                                et_description.setText(user.getAbout());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> hMap = new HashMap<>();

                String username = et_username.getText().toString();
                String describe = et_description.getText().toString();

                if(!user.getUserName().equals(username))
                {
                    hMap.put("userName", username);
                }
                if(!user.getAbout().equals(describe))
                {
                    hMap.put("about", describe);
                }
                if(hMap.size() != 0)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.INVISIBLE);

                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .updateChildren(hMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            btn_save.setVisibility(View.VISIBLE);
                                            Toast.makeText(Setting_Activity.this, "Updated", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Setting_Activity.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                }

            }
        });

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Setting_Activity.this)
                        .setTitle("Logout")
                        .setMessage("Logout your account")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                auth.signOut();
                                Toast.makeText(Setting_Activity.this, "Account Signed out Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Setting_Activity.this, SignInActivity.class));
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        });

        ll_del_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Setting_Activity.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure to delete account")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final FirebaseUser currentUser = auth.getCurrentUser();
                                final String uid = auth.getUid();
                                assert currentUser != null;
                                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            database.getReference().child("Users").child(uid).setValue(null);
                                            Toast.makeText(Setting_Activity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Setting_Activity.this, SignInActivity.class));
                                            finishAffinity();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Setting_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });

        ll_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Setting_Activity.this, About_Us_Activity.class));
            }
        });

        ib_upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null)
            img_profile.setImageURI(data.getData());

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.INVISIBLE);

                HashMap<String, Object> hMap = new HashMap<>();

                String username = et_username.getText().toString();
                String describe = et_description.getText().toString();

                if(!user.getUserName().equals(username))
                {
                    hMap.put("userName", username);
                }
                if(!user.getAbout().equals(describe))
                {
                    hMap.put("about", describe);
                }
                if(hMap.size() != 0)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.INVISIBLE);

                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                            .updateChildren(hMap);
                }

                if(data.getData() != null)
                {
                    Uri sFile = data.getData();

                    final StorageReference reference = storage.getReference().child("profile_pictures")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));

                    reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("profilepic").setValue(uri.toString());
                                    img_profile.setImageURI(sFile);

                                    progressBar.setVisibility(View.INVISIBLE);
                                    btn_save.setVisibility(View.VISIBLE);

                                    Toast.makeText(Setting_Activity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Setting_Activity.this, MainActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}