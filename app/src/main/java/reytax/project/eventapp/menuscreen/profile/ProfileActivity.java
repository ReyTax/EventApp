package reytax.project.eventapp.menuscreen.profile;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.utils.firebase.FirebaseInit;
import reytax.project.eventapp.utils.firebase.CurrentUserData;
import reytax.project.eventapp.utils.firebase.ImageManager;

public class ProfileActivity extends NavigationBarActivity {

    private TextView textViewUsername , textViewName, textViewCountry, textViewCity, textViewDescription;
    private ImageView imageViewProfilePicture;
    private Button buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_profile, contentFrameLayout);

        textViewUsername = findViewById(R.id.activity_profile_textViewUsername);
        textViewName = findViewById(R.id.activity_profile_textViewName);
        textViewCountry = findViewById(R.id.activity_profile_textViewCountry);
        textViewCity = findViewById(R.id.activity_profile_textViewCity);
        textViewDescription =  findViewById(R.id.activity_profile_textViewDescription);
        imageViewProfilePicture = findViewById(R.id.activity_profile_imageViewProfilePicture);
        buttonSettings = findViewById(R.id.activity_profile_buttonSettings);

        String userId = getIntent().getStringExtra("userId");

        if(userId.equals(FirebaseInit.getFirebaseUser().getUid())){
            loadUserData();
            buttonSettings.setVisibility(View.VISIBLE);
            buttonSettings.setEnabled(true);
        }

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSettingsActivity.class));
            }
        });
    }

    private void loadUserData(){

        textViewUsername.setText(CurrentUserData.getUsername());
        textViewName.setText(CurrentUserData.getFirstname() + " " + CurrentUserData.getLastname());
        textViewCountry.setText(CurrentUserData.getCountry());
        textViewCity.setText(CurrentUserData.getCity());
        textViewDescription.setText(CurrentUserData.getDescription());

        if(CurrentUserData.getProfileimage().equals("true")){
            ImageManager.loadProfileImage(imageViewProfilePicture,CurrentUserData.getBytesProfileImage());

        }


    }


}