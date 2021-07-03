package reytax.project.eventapp.menuscreen.calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.search.SearchEventActivity;
import reytax.project.eventapp.menuscreen.event.search.ShowEventActivity;
import reytax.project.eventapp.menuscreen.event.structure.EventStructure;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarOneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarOneFragment newInstance(String param1, String param2) {
        CalendarOneFragment fragment = new CalendarOneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_one, container, false);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("events");
        List<String> uidEvents = new ArrayList<String>();
        uidEvents.add("");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    uidEvents.add(document.getId());
                }


                RecyclerView recyclerView = view.findViewById(R.id.fragment_calendar_one_recyclerView);
                Query query = firebaseFirestore.collection("events").whereIn("uidEvent", uidEvents);
                FirestoreRecyclerOptions<EventStructure> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<EventStructure>().setQuery(query, EventStructure.class).build();

                FirestoreRecyclerAdapter firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<EventStructure, CalendarOneFragment.EventViewHolder>(firestoreRecyclerOptions) {

                    @NonNull
                    @Override
                    public CalendarOneFragment.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_event_element, parent, false);
                        return new CalendarOneFragment.EventViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull CalendarOneFragment.EventViewHolder eventViewHolder, int i, @NonNull EventStructure eventStrucutre) {
                        eventViewHolder.textViewTitle.setText(eventStrucutre.getTitle());
                        eventViewHolder.textViewUsername.setText("Created by " + eventStrucutre.getUsername());

                        eventViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), ShowEventActivity.class);
                                intent.putExtra("uid", eventStrucutre.getUid());
                                intent.putExtra("uidEvent", eventStrucutre.getUidEvent());
                                intent.putExtra("eventType", eventStrucutre.getEventType());
                                intent.putExtra("title", eventStrucutre.getTitle());
                                intent.putExtra("username", eventStrucutre.getUsername());
                                intent.putExtra("description", eventStrucutre.getDescription());
                                intent.putExtra("country", eventStrucutre.getCountry());
                                intent.putExtra("state", eventStrucutre.getState());
                                intent.putExtra("city", eventStrucutre.getCity());
                                intent.putExtra("address", eventStrucutre.getAddress());
                                intent.putExtra("contact", eventStrucutre.getContact());
                                intent.putExtra("dateStart", eventStrucutre.getDateStart());
                                intent.putExtra("dateEnd", eventStrucutre.getDateEnd());
                                intent.putExtra("participantsNumber", eventStrucutre.getParticipantsNumber());
                                intent.putExtra("currentParticipantsNumber", eventStrucutre.getCurrentParticipantsNumber());
                                startActivity(intent);
                            }
                        });
                    }
                };


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                recyclerView.setAdapter(firestoreRecyclerAdapter);
                firestoreRecyclerAdapter.startListening();
            }
        });

        return view;
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
}