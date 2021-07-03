package reytax.project.eventapp.menuscreen.user.friendlist;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.chat.ChatActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.ImageManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListFragment newInstance(String param1, String param2) {
        FriendListFragment fragment = new FriendListFragment();
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
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("friends");
        List<String> uidFriends = new ArrayList<String>();
        uidFriends.add("");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    if (document.get("friendConfirmation").equals("true"))
                        uidFriends.add(document.getId());
                }

                RecyclerView recyclerViewFriends = view.findViewById(R.id.fragment_friend_list_recyclerViewFriends);

                Query query = firebaseFirestore.collection("users").whereIn("uid", uidFriends);

                FirestoreRecyclerOptions<UserStructure> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<UserStructure>().setQuery(query, UserStructure.class).build();
                FirestoreRecyclerAdapter firestoreRecyclerAdapterFriends = new FirestoreRecyclerAdapter<UserStructure, FriendListFragment.UserViewHolder>(firestoreRecyclerOptions) {

                    @NonNull
                    @Override
                    public FriendListFragment.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_element, parent, false);
                        return new FriendListFragment.UserViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FriendListFragment.UserViewHolder userViewHolder, int i, @NonNull UserStructure userStructure) {
                        userViewHolder.textViewUsername.setText(userStructure.getUsername());

                        if (userStructure.getProfileimage().equals("true")) {
                            StorageReference image = FirebaseInitialization.getStorageReference().child("images/" + userStructure.getUid());
                            final long ONE_MEGABYTE = 1024 * 1024;
                            image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    userViewHolder.bytes = bytes;
                                    ImageManager.loadProfileImage(userViewHolder.imageViewProfilePicture, userViewHolder.bytes);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }

                        userViewHolder.cardViewFriend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("uid", userStructure.getUid());
                                intent.putExtra("username", userStructure.getUsername());
                                intent.putExtra("firstname", userStructure.getFirstname());
                                intent.putExtra("lastname", userStructure.getLastname());
                                intent.putExtra("country", userStructure.getCountry());
                                intent.putExtra("city", userStructure.getCity());
                                intent.putExtra("description", userStructure.getDescription());
                                intent.putExtra("profileimage", userStructure.getProfileimage());
                                intent.putExtra("eventscount", userStructure.getEventscount());
                                intent.putExtra("bytes", userViewHolder.bytes);
                                startActivity(intent);
                            }
                        });

                        userViewHolder.imageViewChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity().getApplicationContext(), ChatActivity.class);
                                List<String> list = new ArrayList<>();
                                list.add(FirebaseInitialization.getFirebaseUser().getUid());
                                list.add(userStructure.getUid());
                                java.util.Collections.sort(list);
                                intent.putExtra("uidChat", list.get(0) + list.get(1));
                                intent.putExtra("usernameSender", UserDataManager.getUsername());
                                intent.putExtra("usernameReceiver", userStructure.getUsername());
                                startActivity(intent);
                            }
                        });
                    }
                };

                recyclerViewFriends.setHasFixedSize(true);
                recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                recyclerViewFriends.setAdapter(firestoreRecyclerAdapterFriends);

                firestoreRecyclerAdapterFriends.startListening();
            }
        });

        return view;
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewProfilePicture, imageViewChat;
        private TextView textViewUsername;
        private CardView cardViewFriend;
        private byte[] bytes;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfilePicture = itemView.findViewById(R.id.friend_element_imageViewProfilePicture);
            imageViewChat = itemView.findViewById(R.id.friend_element_imageViewChat);
            textViewUsername = itemView.findViewById(R.id.friend_element_textViewUsername);
            cardViewFriend = itemView.findViewById(R.id.friend_element_cardViewFriend);

        }
    }
}