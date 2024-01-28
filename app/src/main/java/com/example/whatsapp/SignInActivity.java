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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    ProgressDialog progressDialog;

    Button btn_signin;
    EditText et_email, et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if(getSupportActionBar() != null)
                getSupportActionBar().hide();

        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account.");

        btn_signin = findViewById(R.id.btn_signin);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        auth = FirebaseAuth.getInstance();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_email.getText().toString().isEmpty() && !et_password.getText().toString().isEmpty())
                {
                    progressDialog.show();

                    auth.signInWithEmailAndPassword(et_email.getText().toString(), et_password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if(task.isSuccessful())
                                    {
                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
    }
    public void goto_SignUp(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        finish();
    }
}