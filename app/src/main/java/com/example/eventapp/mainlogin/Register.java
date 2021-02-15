package com.example.eventapp.mainlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.eventapp.MainActivity;
import com.example.eventapp.R;
import com.example.eventapp.utility.RegexVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    FirebaseAuth fAuth;
    Button buttonBack,buttonRegister;
    EditText editTextUser,editTextPass,editTextEmail;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        buttonBack = findViewById(R.id.buttonBack);
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextUser = findViewById(R.id.editTextViewUsername);
        editTextPass = findViewById(R.id.editTextViewPassword);
        editTextEmail = findViewById(R.id.editTextTextEmail);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        final Context context = this;

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email,username,password;
                username = editTextUser.getText().toString().trim();
                password = editTextPass.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();

                if(!RegexVerification.isValidUsername(username)){
                    editTextUser.setError("Please enter a valid username: \n 1. Between 6 and 12 characters. \n 2. Use only letters and numbers.");
                    return;
                }
                if(!RegexVerification.isValidPassword(password))
                {
                    editTextPass.setError("Please enter a valid password:\n 1. Between 8 and 15 characters.\n 2. At least one upper and one lower case letter.\n 3. At least a number.");
                    return;
                }
                if(!RegexVerification.isValidEmail(email)){
                    editTextEmail.setError("Please enter a valid email.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            firebaseUser = fAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification();
                            firebaseFirestore = FirebaseFirestore.getInstance();
                            Map<String,Object> user = new HashMap<>();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                            user.put("email",email);
                            user.put("username",username);
                            user.put("realname","none");
                            user.put("phonenumber","none");
                            user.put("homeaddress","none");
                            user.put("profileimage","none");
                            user.put("numberposts","0");

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Account created. Please verify your email.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this,"Error creating the account.",Toast.LENGTH_SHORT).show();
                                }
                            });

                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Register.this,"Failed Creation",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}