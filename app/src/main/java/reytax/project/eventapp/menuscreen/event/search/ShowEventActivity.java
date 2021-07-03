package reytax.project.eventapp.menuscreen.event.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.ImageManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class ShowEventActivity extends NavigationBarActivity {

    private TextView textViewTitle, textViewUsername, textViewDescription, textViewCountry, textViewCity, textViewAddress, textViewContact, textViewParticipantsNumber, textViewDateStart, textViewDateEnd, textViewState;
    private CardView cardViewUser;
    private ProgressBar progressBarParticipantsNumber;
    private Button buttonParticipate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_navigation_bar_frameLayout);
        getLayoutInflater().inflate(R.layout.activity_show_event, contentFrameLayout);

        textViewTitle = findViewById(R.id.activity_show_event_textViewTitle);
        textViewUsername = findViewById(R.id.activity_show_event_textViewUsername);
        textViewDescription = findViewById(R.id.activity_show_event_textViewDescription);
        textViewCountry = findViewById(R.id.activity_show_event_textViewCountry);
        textViewCity = findViewById(R.id.activity_show_event_textViewCity);
        textViewAddress = findViewById(R.id.activity_show_event_textViewAddress);
        textViewContact = findViewById(R.id.activity_show_event_textViewContact);
        textViewParticipantsNumber = findViewById(R.id.activity_show_event_textViewParticipantsNumber);
        textViewDateStart = findViewById(R.id.activity_show_event_textViewDateStart);
        textViewDateEnd = findViewById(R.id.activity_show_event_textViewDateEnd);
        textViewState = findViewById(R.id.activity_show_event_textViewState);
        cardViewUser = findViewById(R.id.activity_show_event_cardViewUser);
        progressBarParticipantsNumber = findViewById(R.id.activity_show_event_progressBarParticipantsNumber);
        buttonParticipate = findViewById(R.id.activity_show_event_buttonParticipate);

        loadEventData(getIntent().getStringExtra("title"), getIntent().getStringExtra("username"), getIntent().getStringExtra("description"), getIntent().getStringExtra("country"), getIntent().getStringExtra("state"), getIntent().getStringExtra("city"), getIntent().getStringExtra("address"), getIntent().getStringExtra("contact"), getIntent().getStringExtra("dateStart"), getIntent().getStringExtra("dateEnd"), getIntent().getStringExtra("participantsNumber"), getIntent().getIntExtra("currentParticipantsNumber", 0));
        UserDataManager.loadUserData(getIntent().getStringExtra("uid"));

        cardViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("uid", getIntent().getStringExtra("uid"));
                startActivity(intent);
            }
        });

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore.collection("events").document(getIntent().getStringExtra("uidEvent")).collection("participants").document(FirebaseInitialization.getFirebaseUser().getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    buttonParticipate.setEnabled(false);
                    buttonParticipate.setBackgroundColor(getColor(R.color.grey));
                }
            }
        });

        documentReference = firebaseFirestore.collection("events").document(getIntent().getStringExtra("uidEvent"));

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("currentParticipantsNumber")).toString()) >= Integer.parseInt(Objects.requireNonNull(documentSnapshot.get("participantsNumber")).toString())) {
                    buttonParticipate.setEnabled(false);
                    buttonParticipate.setBackgroundColor(getColor(R.color.grey));
                }
            }
        });


        buttonParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("events").document(getIntent().getStringExtra("uidEvent")).update("currentParticipantsNumber", FieldValue.increment(1));
                textViewParticipantsNumber.setText(getIntent().getIntExtra("currentParticipantsNumber", 0) + 1 + "/" + getIntent().getStringExtra("participantsNumber"));
                DocumentReference documentReference = firebaseFirestore.collection("events").document(getIntent().getStringExtra("uidEvent")).collection("participants").document(FirebaseInitialization.getFirebaseUser().getUid());

                Map<String, Object> participant = new HashMap<>();
                participant.put("username", UserDataManager.getUsername());

                documentReference.set(participant);

                documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseAuth().getUid()).collection("events").document(getIntent().getStringExtra("uidEvent"));
                Map<String, Object> event = new HashMap<>();
                event.put("dateStart", getIntent().getStringExtra("dateStart"));
                event.put("dateEnd", getIntent().getStringExtra("dateEnd"));
                event.put("eventType", getIntent().getStringExtra("eventType"));
                event.put("title", getIntent().getStringExtra("title"));

                documentReference.set(event);
            }
        });
    }


    private void loadEventData(String title, String username, String description, String country, String state, String city, String address, String contact, String dateStart, String dateEnd, String participantsNumber, int currentParticipantsNumber) {
        textViewTitle.setText(title);
        textViewUsername.setText("Created by " + username);
        textViewDescription.setText(description);
        textViewCountry.setText(country);
        textViewState.setText(state);
        textViewCity.setText(city);
        textViewAddress.setText(address);
        textViewContact.setText(contact);
        textViewDateStart.setText(dateStart);
        textViewDateEnd.setText(dateEnd);
        textViewParticipantsNumber.setText(currentParticipantsNumber + "/" + participantsNumber);
        if (!participantsNumber.equals("")) {
            double progressValue = (double) currentParticipantsNumber / Integer.parseInt(participantsNumber) * 100;
            progressBarParticipantsNumber.setProgress((int) progressValue);
        }


    }
}