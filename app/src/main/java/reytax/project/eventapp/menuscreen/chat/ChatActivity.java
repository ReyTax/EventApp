package reytax.project.eventapp.menuscreen.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.chat.structure.ChatStructure;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.structure.UserStructure;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class ChatActivity extends NavigationBarActivity {

    private FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    private Button buttonSend;
    private EditText editTextMessage;
    private int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_chat, contentFrameLayout);

        buttonSend = findViewById(R.id.activity_chat_buttonSend);
        editTextMessage = findViewById(R.id.activity_chat_editTextMessage);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = findViewById(R.id.activity_chat_recyclerViewMessages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);



        Query query = firebaseFirestore.collection("chats").document(getIntent().getStringExtra("uidChat")).collection("messages").orderBy("creationDate");

        FirestoreRecyclerOptions<ChatStructure> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<ChatStructure>().setQuery(query, ChatStructure.class).build();

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<ChatStructure, UserViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_element, parent, false);
                count = recyclerView.getAdapter().getItemCount();
                return new UserViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i, @NonNull ChatStructure userStructure) {
                userViewHolder.textViewUsername.setText(userStructure.getUsername());
                userViewHolder.textViewMessage.setText(userStructure.getMessage());
            }
        };

        RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
            }
        };


        firestoreRecyclerAdapter.registerAdapterDataObserver(adapterDataObserver);


        recyclerView.setAdapter(firestoreRecyclerAdapter);


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference collectionReference = firebaseFirestore.collection("chats").document(getIntent().getStringExtra("uidChat")).collection("messages");
                Map<String, Object> messageOne = new HashMap<>();
                messageOne.put("username", UserDataManager.getUsernamelocal());
                messageOne.put("message", editTextMessage.getText().toString());
                editTextMessage.setText("");
                messageOne.put("creationDate", Calendar.getInstance().getTime());
                collectionReference.add(messageOne);

            }
        });
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsername,textViewMessage;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.chat_message_element_textViewUsername);
            textViewMessage = itemView.findViewById(R.id.chat_message_element_textViewMessage);

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