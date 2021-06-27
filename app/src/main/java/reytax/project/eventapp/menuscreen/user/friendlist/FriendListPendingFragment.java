package reytax.project.eventapp.menuscreen.user.friendlist;

import android.content.Context;
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
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.UserDataManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendListPendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListPendingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendListPendingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendListPendingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendListPendingFragment newInstance(String param1, String param2) {
        FriendListPendingFragment fragment = new FriendListPendingFragment();
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

    private FirestoreRecyclerAdapter firestoreRecyclerAdapterFriendsPending = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend_list_pending, container, false);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("friends");
        List<String> uidFriendsPending = new ArrayList<String>();
        uidFriendsPending.add("");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {
                    if (document.get("friendConfirmation").equals(FirebaseInitialization.getFirebaseUser().getUid()))
                        uidFriendsPending.add(document.getId());
                }

                RecyclerView recyclerViewFriendsPending = view.findViewById(R.id.fragment_friend_list_pending_recyclerViewFriendsPending);

                Query query = firebaseFirestore.collection("users").whereIn("uid", uidFriendsPending);
                ;

                FirestoreRecyclerOptions<UserStructure> firestoreRecyclerOptionsPending = new FirestoreRecyclerOptions.Builder<UserStructure>().setQuery(query, UserStructure.class).build();

                firestoreRecyclerAdapterFriendsPending = new FirestoreRecyclerAdapter<UserStructure, FriendListPendingFragment.UserPendingViewHolder>(firestoreRecyclerOptionsPending) {

                    @NonNull
                    @Override
                    public FriendListPendingFragment.UserPendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_pending_element, parent, false);
                        return new FriendListPendingFragment.UserPendingViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull FriendListPendingFragment.UserPendingViewHolder userPendingViewHolder, int i, @NonNull UserStructure userStructure) {
                        userPendingViewHolder.textViewUsername.setText(userStructure.getUsername());

                        if (userStructure.getProfileimage().equals("true")) {
                            StorageReference image = FirebaseInitialization.getStorageReference().child("images/" + userStructure.getUid());
                            final long ONE_MEGABYTE = 1024 * 1024;
                            image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    userPendingViewHolder.bytes = bytes;

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });
                        }

                        userPendingViewHolder.cardViewFriendPending.setOnClickListener(new View.OnClickListener() {
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
                                intent.putExtra("bytes", userPendingViewHolder.bytes);
                                startActivity(intent);
                            }
                        });

                        userPendingViewHolder.buttonAccept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DocumentReference documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("friends").document(userStructure.getUid());
                                Map<String, Object> friendship = new HashMap<>();
                                friendship.put("friendConfirmation", "true");

                                documentReference.set(friendship).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        System.out.println(uidFriendsPending);
                                        uidFriendsPending.remove(userStructure.getUid());
                                        Query query = firebaseFirestore.collection("users").whereIn("uid", uidFriendsPending);
                                        ;
                                        FirestoreRecyclerOptions<UserStructure> firestoreRecyclerOptionsPending = new FirestoreRecyclerOptions.Builder<UserStructure>().setQuery(query, UserStructure.class).build();
                                        firestoreRecyclerAdapterFriendsPending.updateOptions(firestoreRecyclerOptionsPending);
                                        System.out.println(uidFriendsPending);

                                        List<String> list = new ArrayList<String>();
                                        list.add(FirebaseInitialization.getFirebaseUser().getUid());
                                        list.add(userStructure.getUid());
                                        java.util.Collections.sort(list);

                                        DocumentReference documentReference = firebaseFirestore.collection("chats").document(list.get(0) + list.get(1));
                                        Map<String, Object> chatData = new HashMap<>();
                                        chatData.put("user1", UserDataManager.getUsernamelocal());
                                        chatData.put("user2", userStructure.getUsername());
                                        documentReference.set(chatData);
                                        /*
                                        documentReference = firebaseFirestore.collection("chats").document(list.get(0) + list.get(1)).collection("messages").document("confirmationMessageOne");
                                        Map<String, Object> messageOne = new HashMap<>();
                                        messageOne.put("username", UserDataManager.getUsernamelocal());
                                        messageOne.put("message", "Welcome " + userStructure.getUsername() + "!");
                                        messageOne.put("creationDate", Calendar.getInstance().getTime());
                                        documentReference.set(messageOne);

                                        documentReference = firebaseFirestore.collection("chats").document(list.get(0) + list.get(1)).collection("messages").document("confirmationMessageTwo");
                                        Map<String, Object> messageTwo = new HashMap<>();
                                        messageTwo.put("username", userStructure.getUsername());
                                        messageTwo.put("message", "Welcome " + UserDataManager.getUsernamelocal() + "!");
                                        messageTwo.put("creationDate", Calendar.getInstance().getTime());
                                        documentReference.set(messageTwo);
                                        */
                                    }
                                });

                                documentReference = firebaseFirestore.collection("users").document(userStructure.getUid()).collection("friends").document(FirebaseInitialization.getFirebaseUser().getUid());
                                documentReference.set(friendship).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                            }
                        });

                        userPendingViewHolder.buttonReject.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DocumentReference documentReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("friends").document(userStructure.getUid());
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                                documentReference = firebaseFirestore.collection("users").document(userStructure.getUid()).collection("friends").document(FirebaseInitialization.getFirebaseUser().getUid());
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });
                            }
                        });
                    }
                };


                recyclerViewFriendsPending.setHasFixedSize(true);
                recyclerViewFriendsPending.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                recyclerViewFriendsPending.setAdapter(firestoreRecyclerAdapterFriendsPending);
                firestoreRecyclerAdapterFriendsPending.startListening();
            }
        });

        return view;
    }

    private class UserPendingViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername;
        private Button buttonAccept, buttonReject;
        private CardView cardViewFriendPending;
        private byte[] bytes;

        public UserPendingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.friend_pending_element_textViewUsername);
            buttonAccept = itemView.findViewById(R.id.friend_pending_element_buttonAccept);
            buttonReject = itemView.findViewById(R.id.friend_pending_element_buttonReject);
            cardViewFriendPending = itemView.findViewById(R.id.friend_pending_element_cardViewFriendPending);

        }
    }
}