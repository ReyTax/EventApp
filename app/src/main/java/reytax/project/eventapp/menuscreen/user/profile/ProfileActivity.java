package reytax.project.eventapp.menuscreen.user.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.search.SearchEventActivity;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.UserDataManager;
import reytax.project.eventapp.utils.firebase.ImageManager;

public class ProfileActivity extends NavigationBarActivity {

    private TextView textViewUsername, textViewName, textViewCountry, textViewCity, textViewDescription, textViewEventsCount;
    private ImageView imageViewProfilePicture;
    private Button buttonSettings;
    private static Boolean isThisUserProfile = false;
    private ConstraintLayout constraintLayoutEventscount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_profile, contentFrameLayout);

        textViewUsername = findViewById(R.id.activity_profile_textViewUsername);
        textViewName = findViewById(R.id.activity_profile_textViewName);
        textViewCountry = findViewById(R.id.activity_profile_textViewCountry);
        textViewCity = findViewById(R.id.activity_profile_textViewCity);
        textViewDescription = findViewById(R.id.activity_profile_textViewDescription);
        textViewEventsCount = findViewById(R.id.activity_profile_textViewEventsCount);
        imageViewProfilePicture = findViewById(R.id.activity_profile_imageViewProfilePicture);
        buttonSettings = findViewById(R.id.activity_profile_buttonSettings);
        constraintLayoutEventscount = findViewById(R.id.activity_profile_constraintLayoutEventsCount);


        String uid = getIntent().getStringExtra("uid");
        String username = getIntent().getStringExtra("username");

        if (uid.equals(FirebaseInitialization.getFirebaseUser().getUid())) {
            isThisUserProfile = true;
            showUserData(UserDataManager.getUsernamelocal(), UserDataManager.getFirstnamelocal() + " " + UserDataManager.getLastnamelocal(), UserDataManager.getCountrylocal(), UserDataManager.getCitylocal(), UserDataManager.getDescriptionlocal(), UserDataManager.getProfileimagelocal(), UserDataManager.getEventscountLocal(), UserDataManager.getBytesProfileImagelocal());
            buttonSettings.setVisibility(View.VISIBLE);
            buttonSettings.setEnabled(true);
        } else if (username != null) {
            isThisUserProfile = false;
            showUserData(getIntent().getStringExtra("username"), getIntent().getStringExtra("firstname") + " " + getIntent().getStringExtra("lastname"), getIntent().getStringExtra("country"), getIntent().getStringExtra("city"), getIntent().getStringExtra("description"), getIntent().getStringExtra("profileimage"), getIntent().getIntExtra("eventscount",0), getIntent().getByteArrayExtra("bytes"));
        } else {
            isThisUserProfile = false;
            showUserData(UserDataManager.getUsername(), UserDataManager.getFirstname() + " " + UserDataManager.getLastname(), UserDataManager.getCountry(), UserDataManager.getCity(), UserDataManager.getDescription(), UserDataManager.getProfileimage(), UserDataManager.getEventscount(), UserDataManager.getBytesProfileImage());
        }

        constraintLayoutEventscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchEventActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);

            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSettingsActivity.class));
            }
        });
    }

    private void showUserData(String username, String name, String country, String city, String description, String profileImage, int eventscount, byte[] bytes) {
        textViewUsername.setText(username);
        textViewName.setText(name);
        textViewCountry.setText(country);
        textViewCity.setText(city);
        textViewDescription.setText(description);
        textViewEventsCount.setText(String.valueOf(eventscount));
        if (profileImage.equals("true")) {
            ImageManager.loadProfileImage(imageViewProfilePicture, bytes);
        }
    }

    public static Boolean getIsThisUserProfile() {
        return isThisUserProfile;
    }

    @Override
    protected void onDestroy() {
        isThisUserProfile = false;
        super.onDestroy();
    }

}