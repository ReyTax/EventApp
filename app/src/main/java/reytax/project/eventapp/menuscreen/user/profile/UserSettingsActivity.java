package reytax.project.eventapp.menuscreen.user.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import reytax.project.eventapp.utils.activity.DataValidation;
import reytax.project.eventapp.utils.api.filter.ProfanityApi;
import reytax.project.eventapp.utils.api.places.CountryStateCityApi;
import reytax.project.eventapp.utils.functions.DataConvert;
import reytax.project.eventapp.utils.firebase.ImageManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class UserSettingsActivity extends NavigationBarActivity {

    private EditText editTextUsername, editTextFirstname, editTextLastname, editTextPhonenumber, editTextDescription;
    private AutoCompleteTextView autoCompleteTextViewCountry, autoCompleteTextViewState, autoCompleteTextViewCity;
    private TextView textViewImageInfo;
    private Button buttonEdit, buttonSave;
    private ImageView imageViewProfilePicture;
    private boolean isEditable;
    private Uri uriImage;
    private boolean hasValidImage;
    private byte[] inputBytes;
    private String countryValue = "", stateValue = "";
    private boolean lockCityRequest = false, lockStateRequest = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_navigation_bar_frameLayout);
        getLayoutInflater().inflate(R.layout.activity_user_settings, contentFrameLayout);

        editTextUsername = findViewById(R.id.activity_user_settings_editTextUsername);
        editTextFirstname = findViewById(R.id.activity_user_settings_editTextFirstName);
        editTextLastname = findViewById(R.id.activity_user_settings_editTextLastName);
        autoCompleteTextViewCountry = findViewById(R.id.activity_user_settings_autoCompleteTextViewCountry);
        autoCompleteTextViewState = findViewById(R.id.activity_user_settings_autoCompleteTextViewState);
        autoCompleteTextViewCity = findViewById(R.id.activity_user_settings_autoCompleteTextViewCity);
        editTextPhonenumber = findViewById(R.id.activity_user_settings_editTextPhonenumber);
        editTextDescription = findViewById(R.id.activity_user_settings_editTextDescription);
        textViewImageInfo = findViewById(R.id.activity_user_settings_textViewImageInfo);
        buttonEdit = findViewById(R.id.activity_user_settings_buttonEdit);
        buttonSave = findViewById(R.id.activity_user_settings_buttonSave);
        imageViewProfilePicture = findViewById(R.id.activity_user_settings_imageViewProfilePicture);

        setAutoCompleteTextViewCountryAdapter();

        autoCompleteTextViewCountry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && DataValidation.checkCountry(autoCompleteTextViewCountry.getText().toString()) && !countryValue.equals(autoCompleteTextViewCountry.getText().toString())) {
                    CountryStateCityApi.resetStates();
                    autoCompleteTextViewState.setText("");
                    autoCompleteTextViewCity.setText("");
                    lockStateRequest = false;
                }
                countryValue = autoCompleteTextViewCountry.getText().toString();

            }
        });

        autoCompleteTextViewState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DataValidation.checkCountry(autoCompleteTextViewCountry.getText().toString()) && CountryStateCityApi.getStates().isEmpty() && lockStateRequest == false) {
                    lockStateRequest = true;
                    CountryStateCityApi countryStateCityApi = new CountryStateCityApi();
                    countryStateCityApi.execute("get_states", autoCompleteTextViewCountry.getText().toString());
                }
                setAutoCompleteTextViewStateAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteTextViewState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && DataValidation.checkState(autoCompleteTextViewState.getText().toString()) && !stateValue.equals(autoCompleteTextViewState.getText().toString())) {
                    CountryStateCityApi.resetCities();
                    autoCompleteTextViewCity.setText("");
                    lockCityRequest = false;
                }
                stateValue = autoCompleteTextViewState.getText().toString();

            }
        });

        autoCompleteTextViewCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (DataValidation.checkState(autoCompleteTextViewState.getText().toString()) && CountryStateCityApi.getCities().isEmpty() && lockCityRequest == false) {
                    lockCityRequest = true;
                    CountryStateCityApi countryStateCityApi = new CountryStateCityApi();
                    countryStateCityApi.execute("get_cities", autoCompleteTextViewState.getText().toString());
                }
                setAutoCompleteTextViewCityAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadUserData();
        isEditable = false;

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockCityRequest = false;
                lockStateRequest = false;

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
                if (isEditable == true) {
                    lockCityRequest = false;
                    lockStateRequest = false;


                    if (DataValidation.checkCountry(autoCompleteTextViewCountry.getText().toString()) && CountryStateCityApi.getStates().isEmpty() && !lockStateRequest) {
                        lockStateRequest = true;
                        CountryStateCityApi countryStateCityApi = new CountryStateCityApi();
                        countryStateCityApi.execute("get_states", autoCompleteTextViewCountry.getText().toString());
                    }

                    int timer = 0;
                    while (CountryStateCityApi.getStates().isEmpty() && timer < 3000)
                        try {
                            Thread.sleep(50);
                            timer += 50;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    if (DataValidation.checkState(autoCompleteTextViewState.getText().toString()) && CountryStateCityApi.getCities().isEmpty() && !lockCityRequest) {
                        lockCityRequest = true;
                        CountryStateCityApi countryStateCityApi = new CountryStateCityApi();
                        countryStateCityApi.execute("get_cities", autoCompleteTextViewState.getText().toString());
                    }

                    timer = 0;
                    while (CountryStateCityApi.getCities().isEmpty() && timer < 3000)
                        try {
                            Thread.sleep(50);
                            timer += 50;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    boolean validData = true;
                    if (hasValidImage) {
                        ImageManager.uploadProfileImage(inputBytes);
                        UserDataManager.setBytesProfileImagelocal(inputBytes);
                    }
                    if (!DataValidation.checkUsername(editTextUsername.getText().toString())) {
                        validData = false;
                        editTextUsername.setError("Please enter a valid username: \n 1. Between 6 and 12 letters. \n 2. Use only letters and numbers.");
                    }
                    if (!DataValidation.checkName(editTextFirstname.getText().toString())) {
                        validData = false;
                        editTextFirstname.setError("Please enter a valid name made only by letters.");
                    }
                    if (!DataValidation.checkName(editTextLastname.getText().toString())) {
                        validData = false;
                        editTextLastname.setError("Please enter a valid name made only by letters.");
                    }

                    if (!DataValidation.checkCountry(autoCompleteTextViewCountry.getText().toString())) {
                        validData = false;
                        autoCompleteTextViewCountry.setError("Please enter a valid country.");
                    }
                    if (!DataValidation.checkState(autoCompleteTextViewState.getText().toString())) {
                        validData = false;
                        autoCompleteTextViewState.setError("Please enter a valid state.");
                    }
                    if (!DataValidation.checkCity(autoCompleteTextViewCity.getText().toString())) {
                        validData = false;
                        autoCompleteTextViewCity.setError("Please enter a valid city.");
                    }


                    if (!DataValidation.checkPhonenumber(editTextPhonenumber.getText().toString())) {
                        validData = false;
                        editTextPhonenumber.setError("Please enter a valid phone number.");
                    }

                    DataValidation.checkDescription(editTextDescription.getText().toString());
                    timer = 0;

                    while (!ProfanityApi.getIsDone() && timer < 3000) {
                        try {
                            Thread.sleep(1);
                            timer += 1;
                            System.out.println(timer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    if (ProfanityApi.getIsProfane()) {
                        validData = false;
                        editTextDescription.setError("Profanities not allowed.");
                    }

                    if (validData) {
                        UserDataManager.uploadLocalProfileData(editTextUsername.getText().toString(), editTextFirstname.getText().toString(), editTextLastname.getText().toString(), autoCompleteTextViewCountry.getText().toString(), autoCompleteTextViewState.getText().toString(), autoCompleteTextViewCity.getText().toString(), editTextPhonenumber.getText().toString(), editTextDescription.getText().toString());
                        disableEdit();
                    }
                }
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
        autoCompleteTextViewCountry.setText(UserDataManager.getCountrylocal());
        autoCompleteTextViewState.setText(UserDataManager.getStatelocal());
        autoCompleteTextViewCity.setText(UserDataManager.getCitylocal());
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
                ImageManager.loadProfileImage(imageViewProfilePicture, inputBytes);
            } else {
                hasValidImage = false;
            }
        }
    }

    private void setAutoCompleteTextViewCountryAdapter() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CountryStateCityApi.getCountries());
        autoCompleteTextViewCountry.setThreshold(1);
        autoCompleteTextViewCountry.setAdapter(arrayAdapter);
    }

    private void setAutoCompleteTextViewStateAdapter() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CountryStateCityApi.getStates());
        autoCompleteTextViewState.setThreshold(1);
        autoCompleteTextViewState.setAdapter(arrayAdapter);
    }

    private void setAutoCompleteTextViewCityAdapter() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CountryStateCityApi.getCities());
        autoCompleteTextViewCity.setThreshold(1);
        autoCompleteTextViewCity.setAdapter(arrayAdapter);
    }

    private void enableEdit() {
        isEditable = true;
        textViewImageInfo.setVisibility(View.VISIBLE);
        editTextUsername.setEnabled(true);
        editTextFirstname.setEnabled(true);
        editTextLastname.setEnabled(true);
        autoCompleteTextViewCountry.setEnabled(true);
        autoCompleteTextViewState.setEnabled(true);
        autoCompleteTextViewCity.setEnabled(true);
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
        autoCompleteTextViewCountry.setEnabled(false);
        autoCompleteTextViewState.setEnabled(false);
        autoCompleteTextViewCity.setEnabled(false);
        editTextPhonenumber.setEnabled(false);
        editTextDescription.setEnabled(false);
    }


}