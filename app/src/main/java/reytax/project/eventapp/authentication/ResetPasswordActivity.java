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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.activity.RegexVerification;
import reytax.project.eventapp.utils.activity.Scrollfunction;
import reytax.project.eventapp.utils.firebase.FirebaseInit;

public class ResetPasswordActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private EditText editTextEmail;
    private Button buttonBackLogin, buttonSend;
    private ProgressBar progressBar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        context = this;

        scrollView = findViewById(R.id.activity_reset_password_scrollView);
        editTextEmail = findViewById(R.id.activity_reset_password_editTextEmail);
        buttonBackLogin = findViewById(R.id.activity_reset_password_buttonBackLogin);
        buttonSend = findViewById(R.id.activity_reset_password_buttonSend);
        progressBar = findViewById(R.id.activity_reset_progressBar);

        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("CHANGE FOCUS");
                Scrollfunction.scrollDown(scrollView);
            }
        });

        buttonBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseResetPassword();
            }
        });
    }

    private void fireBaseResetPassword(){
        String email;
        email = editTextEmail.getText().toString().trim();


        if(!RegexVerification.isValidEmail(email)){
            editTextEmail.setError("Please enter a valid email.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        FirebaseInit.getFirebaseAuth().sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ResetPasswordActivity.this,"The email has been sent, please check your address.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResetPasswordActivity.this,"Error sending the email, please check again the email.",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}