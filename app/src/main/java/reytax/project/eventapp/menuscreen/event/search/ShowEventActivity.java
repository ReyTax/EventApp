package reytax.project.eventapp.menuscreen.event.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.utils.firebase.ImageManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class ShowEventActivity extends NavigationBarActivity {

    private TextView textViewTitle, textViewUsername, textViewDescription, textViewCountry, textViewCity, textViewAddress, textViewContact;
    private CardView cardViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_show_event, contentFrameLayout);

        textViewTitle = findViewById(R.id.activity_show_event_textViewTitle);
        textViewUsername = findViewById(R.id.activity_show_event_textViewUsername);
        textViewDescription = findViewById(R.id.activity_show_event_textViewDescription);
        textViewCountry = findViewById(R.id.activity_show_event_textViewCountry);
        textViewCity = findViewById(R.id.activity_show_event_textViewCity);
        textViewAddress = findViewById(R.id.activity_show_event_textViewAddress);
        textViewContact = findViewById(R.id.activity_show_event_textViewContact);
        cardViewUser = findViewById(R.id.activity_show_event_cardViewUser);

        loadEventData(getIntent().getStringExtra("title"),getIntent().getStringExtra("username"),getIntent().getStringExtra("description"),getIntent().getStringExtra("country"),getIntent().getStringExtra("city"),getIntent().getStringExtra("address"),getIntent().getStringExtra("contact"));
        UserDataManager.loadUserData(getIntent().getStringExtra("uid"));

        cardViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("uid", getIntent().getStringExtra("uid"));
                startActivity(intent);
            }
        });

    }

    private void loadEventData(String title, String username, String description, String country, String city, String address, String contact) {
        textViewTitle.setText(title);
        textViewUsername.setText("Created by " + username);
        textViewDescription.setText(description);
        textViewCountry.setText(country);
        textViewCity.setText(city);
        textViewAddress.setText(address);
        textViewContact.setText(contact);

    }
}