package reytax.project.eventapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

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

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.activity.RegexVerification;
import reytax.project.eventapp.utils.activity.Scrollfunction;
import reytax.project.eventapp.utils.firebase.FirebaseInit;

public class RegisterActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private EditText editTextPassword, editTextEmail, editTextUsername;
    private Button buttonBackLogin, buttonRegister;
    private ProgressBar progressBar;

    private Context context;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;

        scrollView = findViewById(R.id.activity_register_scrollView);
        editTextPassword = findViewById(R.id.activity_register_editTextPassword);
        editTextEmail = findViewById(R.id.activity_register_editTextEmail);
        editTextUsername = findViewById(R.id.activity_register_editTextUsername);
        buttonBackLogin = findViewById(R.id.activity_register_buttonBackLogin);
        buttonRegister = findViewById(R.id.activity_register_buttonRegister);
        progressBar = findViewById(R.id.activity_register_progressBar);


        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    Scrollfunction.scrollDown(scrollView);
            }
        });

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    Scrollfunction.scrollDown(scrollView);
            }
        });

        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    Scrollfunction.scrollDown(scrollView);
            }
        });


        buttonBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseRegistration();
            }
        });
    }

    private void fireBaseRegistration(){
        final String email,username,password;
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();

        boolean validData = true;

        if(!RegexVerification.isValidUsername(username)){
            editTextUsername.setError("Please enter a valid username: \n 1. Between 6 and 12 characters. \n 2. Use only letters and numbers.");
            validData = false;
        }
        if(!RegexVerification.isValidPassword(password))
        {
            editTextPassword.setError("Please enter a valid password:\n 1. Between 8 and 15 characters.\n 2. At least one upper and one lower case letter.\n 3. At least a number.");
            validData = false;
        }
        if(!RegexVerification.isValidEmail(email)){
            editTextEmail.setError("Please enter a valid email.");
            validData = false;
        }
        if(validData == false){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        FirebaseInit.getFirebaseAuth().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseInit.initialize();
                    FirebaseInit.getFirebaseUser().sendEmailVerification();
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    Map<String,Object> user = new HashMap<>();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(FirebaseInit.getFirebaseUser().getUid());
                    user.put("email",email);
                    user.put("username",username);
                    user.put("firstname","");
                    user.put("lastname","");
                    user.put("phonenumber","");
                    user.put("country","");
                    user.put("city","");
                    user.put("description","");
                    user.put("profileimage","");
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this,"Account created. Please verify your email.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this,"Error creating the account.",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Failed Creation",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}