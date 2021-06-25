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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.MenuScreenActivity;
import reytax.project.eventapp.utils.activity.RegexVerification;
import reytax.project.eventapp.utils.activity.Scrollfunction;
import reytax.project.eventapp.utils.api.CountryStateCityApi;
import reytax.project.eventapp.utils.api.ProfanityApi;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class MainActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private EditText editTextPassword, editTextEmail;
    private Button buttonLogin,buttonResetPassword,buttonRegister;
    private ProgressBar progressBar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        CountryStateCityApi countryStateCityApi = new CountryStateCityApi();
        countryStateCityApi.execute("get_token_and_countries");



        scrollView = findViewById(R.id.activity_main_scrollView);
        editTextPassword = findViewById(R.id.activity_main_editTextPassword);
        editTextEmail = findViewById(R.id.activity_main_editTextEmail);
        buttonLogin = findViewById(R.id.activity_main_buttonLogin);
        buttonResetPassword = findViewById(R.id.activity_main_buttonResetPassword);
        buttonRegister = findViewById(R.id.activity_main_buttonRegister);
        progressBar = findViewById(R.id.activity_main_progressBar);

        FirebaseInitialization.initialize();
        if(FirebaseInitialization.getFirebaseAuth().getCurrentUser()!=null && FirebaseInitialization.getFirebaseUser().isEmailVerified())
        {
            UserDataManager.loadLocalUserData();
            startActivity(new Intent(this, MenuScreenActivity.class));
            finish();
        }


        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ResetPasswordActivity.class));
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, RegisterActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseLogin();
            }
        });
    }

    private void fireBaseLogin(){
        String email,password;
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        boolean validData = true;

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

        FirebaseInitialization.getFirebaseAuth().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseInitialization.initialize();
                    if(FirebaseInitialization.getFirebaseUser().isEmailVerified()){
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        UserDataManager.loadLocalUserData();
                        startActivity(new Intent(context, MenuScreenActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Please verify your email.",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        FirebaseInitialization.getFirebaseUser().sendEmailVerification();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}