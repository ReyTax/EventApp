package reytax.project.eventapp.menuscreen.user.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.utils.functions.DataConvert;
import reytax.project.eventapp.utils.firebase.ImageManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class UserSettingsActivity extends NavigationBarActivity {

    private EditText editTextUsername, editTextFirstname, editTextLastname, editTextCountry, editTextCity, editTextPhonenumber, editTextDescription;
    private TextView textViewImageInfo;
    private Button buttonEdit, buttonSave;
    private ImageView imageViewProfilePicture;
    private boolean isEditable;
    private Uri uriImage;
    private boolean hasValidImage;
    private byte[] inputBytes;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_user_settings, contentFrameLayout);

        editTextUsername = findViewById(R.id.activity_user_settings_editTextUsername);
        editTextFirstname = findViewById(R.id.activity_user_settings_editTextFirstName);
        editTextLastname = findViewById(R.id.activity_user_settings_editTextLastName);
        editTextCountry = findViewById(R.id.activity_user_settings_editTextCountry);
        editTextCity = findViewById(R.id.activity_user_settings_editTextCity);
        editTextPhonenumber = findViewById(R.id.activity_user_settings_editTextPhonenumber);
        editTextDescription = findViewById(R.id.activity_user_settings_editTextDescription);
        textViewImageInfo = findViewById(R.id.activity_user_settings_textViewImageInfo);
        buttonEdit = findViewById(R.id.activity_user_settings_buttonEdit);
        buttonSave = findViewById(R.id.activity_user_settings_buttonSave);
        imageViewProfilePicture = findViewById(R.id.activity_user_settings_imageViewProfilePicture);

        loadUserData();
        isEditable = false;

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    disableEdit();
                } else {
                    enableEdit();
                }
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasValidImage) {
                    ImageManager.uploadProfileImage(inputBytes);
                    UserDataManager.setBytesProfileImagelocal(inputBytes);
                }
                UserDataManager.uploadLocalProfileData(editTextUsername.getText().toString(), editTextFirstname.getText().toString(), editTextLastname.getText().toString(), editTextCountry.getText().toString(), editTextCity.getText().toString(), editTextPhonenumber.getText().toString(), editTextDescription.getText().toString());
                disableEdit();
            }
        });


        imageViewProfilePicture.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isEditable) {
                    hasValidImage = false;
                    getLocalImage();
                }
                return false;
            }
        });
    }

    private void loadUserData() {
        editTextUsername.setText(UserDataManager.getUsernamelocal());
        editTextFirstname.setText(UserDataManager.getFirstnamelocal());
        editTextLastname.setText(UserDataManager.getLastnamelocal());
        editTextCountry.setText(UserDataManager.getCountrylocal());
        editTextCity.setText(UserDataManager.getCitylocal());
        editTextPhonenumber.setText(UserDataManager.getPhonenumberlocal());
        editTextDescription.setText(UserDataManager.getDescriptionlocal());
        if (UserDataManager.getProfileimagelocal().equals("true")) {
            ImageManager.loadProfileImage(imageViewProfilePicture, UserDataManager.getBytesProfileImagelocal());
        }
    }


    private void getLocalImage() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGalleryIntent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                hasValidImage = true;
                uriImage = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(uriImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    inputBytes = DataConvert.getBytes(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ImageManager.loadProfileImage(imageViewProfilePicture, UserDataManager.getBytesProfileImagelocal());
            } else {
                hasValidImage = false;
            }
        }
    }


    private void enableEdit() {
        isEditable = true;
        textViewImageInfo.setVisibility(View.VISIBLE);
        editTextUsername.setEnabled(true);
        editTextFirstname.setEnabled(true);
        editTextLastname.setEnabled(true);
        editTextCountry.setEnabled(true);
        editTextCity.setEnabled(true);
        editTextPhonenumber.setEnabled(true);
        editTextDescription.setEnabled(true);
    }

    private void disableEdit() {
        loadUserData();
        isEditable = false;
        textViewImageInfo.setVisibility(View.INVISIBLE);
        editTextUsername.setEnabled(false);
        editTextFirstname.setEnabled(false);
        editTextLastname.setEnabled(false);
        editTextCountry.setEnabled(false);
        editTextCity.setEnabled(false);
        editTextPhonenumber.setEnabled(false);
        editTextDescription.setEnabled(false);
    }


}