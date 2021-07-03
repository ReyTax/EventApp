package reytax.project.eventapp.menuscreen.user.friendlist;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.create.CreateEventPageOneFragment;
import reytax.project.eventapp.menuscreen.event.create.CreateEventPageTwoFragment;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.profile.ProfileActivity;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;
import reytax.project.eventapp.utils.firebase.ImageManager;

public class FriendListActivity extends NavigationBarActivity {

    private Button buttonFriendsList, buttonFriendsListPending;
    private int currentFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_navigation_bar_frameLayout);
        getLayoutInflater().inflate(R.layout.activity_friend_list, contentFrameLayout);

        buttonFriendsList = findViewById(R.id.activity_friend_list_buttonFriendList);
        buttonFriendsListPending = findViewById(R.id.activity_friend_list_buttonFriendListPending);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_friend_list_frameLayout, new FriendListFragment(), "friendListFragment").commit();


        buttonFriendsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment == 2) {
                    currentFragment = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_friend_list_frameLayout, new FriendListFragment(), "friendListFragment").commit();
                }

            }
        });

        buttonFriendsListPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment == 1) {
                    currentFragment = 2;
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_friend_list_frameLayout, new FriendListPendingFragment(), "friendListPendingFragment").commit();
                }
            }
        });
    }

}