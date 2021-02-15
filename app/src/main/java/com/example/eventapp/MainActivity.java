package com.example.eventapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eventapp.mainlogin.Register;
import com.example.eventapp.mainlogin.ResetPassword;
import com.example.eventapp.mainscreen.MainScreen;
import com.example.eventapp.utility.RegexVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // access modifiers later
    Intent intentRegister;
    FirebaseAuth fAuth;
    Button buttonRegister,buttonLogin,buttonResetPassword;
    EditText editTextEmail,editTextPass;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentRegister = new Intent(this, Register.class);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonResetPassword = findViewById(R.id.buttonResetPass);
        editTextEmail = findViewById(R.id.editTextViewEmail);
        editTextPass = findViewById(R.id.editTextViewPassword);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();

        final Context context = this;

        if(fAuth.getCurrentUser()!=null && firebaseUser.isEmailVerified())
        {
            startActivity(new Intent(this, MainScreen.class));
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;

                email = editTextEmail.getText().toString().trim();
                password = editTextPass.getText().toString().trim();

                if(!RegexVerification.isValidEmail(email)){
                    editTextEmail.setError("Please enter a valid email.");
                    return;
                }
                if(!RegexVerification.isValidPassword(password))
                {
                    editTextPass.setError("Please enter a valid password:\n 1. Between 8 and 15 characters.\n 2. At least one upper and one lower case letter.\n 3. At least a number.");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseUser = fAuth.getCurrentUser();
                            if(firebaseUser.isEmailVerified()){
                                Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, MainScreen.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Please verify your email.",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                                firebaseUser.sendEmailVerification();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ResetPassword.class));
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentRegister);
            }
        });
    }

}