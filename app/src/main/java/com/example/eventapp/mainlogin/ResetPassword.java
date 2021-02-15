package com.example.eventapp.mainlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventapp.MainActivity;
import com.example.eventapp.R;
import com.example.eventapp.utility.RegexVerification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    Button buttonBack,buttonSend;
    Context context;
    EditText editTextEmail;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        context = this;
        buttonBack = findViewById(R.id.buttonBack);
        buttonSend = findViewById(R.id.buttonSend);
        editTextEmail = findViewById(R.id.editTextResetPasswordEmail);
        fAuth = FirebaseAuth.getInstance();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                email = editTextEmail.getText().toString().trim();

                if(!RegexVerification.isValidEmail(email)){
                    editTextEmail.setError("Please enter a valid email.");
                    return;
                }

                fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ResetPassword.this,"The email has been sent, please check your address.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPassword.this,"Error sending the email, please check again the email.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });
    }


}