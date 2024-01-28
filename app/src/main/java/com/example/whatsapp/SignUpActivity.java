package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    Button btn_signup;
    EditText et_email, et_password, et_username;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_signup = findViewById(R.id.btn_signup);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Creating your account.");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_email.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty() && !et_username.getText().toString().isEmpty())
                {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful()) {
                                        User user = new User(et_username.getText().toString(), et_email.getText().toString(), et_password.getText().toString());
                                        String id = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(user);

                                        Toast.makeText(SignUpActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void goto_SignIn(View view) {
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        finish();
    }
}