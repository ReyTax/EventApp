package reytax.project.eventapp.menuscreen.event.search;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.structure.EventStructure;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;

public class SearchEventActivity extends NavigationBarActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search_event, contentFrameLayout);
        context = this;

        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.activity_search_event_recyclerView);

        Query query = firebaseFirestore.collection("events").orderBy("creationDate", Query.Direction.DESCENDING);

        if (getIntent().getStringExtra("uid") != null) {
            query = firebaseFirestore.collection("events").orderBy("creationDate", Query.Direction.DESCENDING).whereEqualTo("uid",getIntent().getStringExtra("uid"));
        }



        FirestoreRecyclerOptions<EventStructure> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<EventStructure>().setQuery(query, EventStructure.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<EventStructure, EventViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_event_element, parent, false);
                return new EventViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SearchEventActivity.EventViewHolder eventViewHolder, int i, @NonNull EventStructure eventStrucutre) {
                eventViewHolder.textViewTitle.setText(eventStrucutre.getTitle());
                eventViewHolder.textViewUsername.setText("Created by " + eventStrucutre.getUsername());

                eventViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ShowEventActivity.class);
                        intent.putExtra("uid", eventStrucutre.getUid());
                        intent.putExtra("title", eventStrucutre.getTitle());
                        intent.putExtra("username", eventStrucutre.getUsername());
                        intent.putExtra("description", eventStrucutre.getDescription());
                        intent.putExtra("country", eventStrucutre.getCountry());
                        intent.putExtra("city", eventStrucutre.getCity());
                        intent.putExtra("address", eventStrucutre.getAddress());
                        intent.putExtra("contact", eventStrucutre.getContact());
                        startActivity(intent);
                    }
                });
            }
        };


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

    private class EventViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewUsername;
        private CardView cardView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.search_event_element_textViewTitle);
            textViewUsername = itemView.findViewById(R.id.search_event_element_textViewUsername);
            cardView = itemView.findViewById(R.id.search_event_element_cardView);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();
    }
}