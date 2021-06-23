package reytax.project.eventapp.menuscreen.user.search;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.search.SearchEventActivity;
import reytax.project.eventapp.menuscreen.event.search.ShowEventActivity;
import reytax.project.eventapp.menuscreen.event.structure.EventStructure;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.ImageManager;

public class SearchUserActivity extends NavigationBarActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_search_user, contentFrameLayout);
        context = this;

        firebaseFirestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.activity_search_user_recyclerView);

        Query query = firebaseFirestore.collection("users");

        FirestoreRecyclerOptions<UserStructure> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UserStructure>().setQuery(query, UserStructure.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<UserStructure, SearchUserActivity.UserViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public SearchUserActivity.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_element, parent, false);
                return new SearchUserActivity.UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull SearchUserActivity.UserViewHolder userViewHolder, int i, @NonNull UserStructure userStructure) {
                userViewHolder.textViewUsername.setText(userStructure.getUsername());
                userViewHolder.textViewCountry.setText(userStructure.getCountry());

                if(userStructure.getProfileimage().equals("true")){
                    StorageReference image = FirebaseInitialization.getStorageReference().child("images/"+ userStructure.getUid());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            userViewHolder.bytes = bytes;
                            ImageManager.loadProfileImage(userViewHolder.imageViewProfilePicture,userViewHolder.bytes);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }

                userViewHolder.cardViewSearchUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra("uid", userStructure.getUid());
                        intent.putExtra("username", userStructure.getUsername());
                        intent.putExtra("firstname",userStructure.getFirstname());
                        intent.putExtra("lastname",userStructure.getLastname());
                        intent.putExtra("country",userStructure.getCountry());
                        intent.putExtra("city",userStructure.getCity());
                        intent.putExtra("description",userStructure.getDescription());
                        intent.putExtra("profileimage",userStructure.getProfileimage());
                        intent.putExtra("bytes",userViewHolder.bytes);
                        startActivity(intent);
                    }
                });
            }
        };


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewProfilePicture;
        private TextView textViewUsername, textViewCountry;
        private CardView cardViewSearchUser;
        private byte[] bytes;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfilePicture = itemView.findViewById(R.id.search_user_element_imageViewProfilePicture);
            textViewUsername = itemView.findViewById(R.id.search_user_element_textViewUsername);
            textViewCountry = itemView.findViewById(R.id.search_user_element_textViewCountry);
            cardViewSearchUser = itemView.findViewById(R.id.search_user_element_cardView);

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