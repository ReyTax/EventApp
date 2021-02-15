package com.example.eventapp.mainscreen.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventapp.MainActivity;
import com.example.eventapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Button buttonSignout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ImageView imageViewProfilePicture;
    StorageReference storageReference;
    DocumentReference documentReference;
    String userUsername, userRealName,userEmail,userPhoneNumber,userHomeAddress,userProfileImage,userPostsNumber;
    EditText editTextEmail,editTextRealName,editTextPhoneNumber,editTextHomeAddress;
    TextView textViewPostNumber,textViewUsername;
    Button buttonSettings,buttonAccept;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        buttonSignout = view.findViewById(R.id.buttonSignout);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        imageViewProfilePicture = view.findViewById(R.id.imageViewProfilePicture);
        editTextEmail = view.findViewById(R.id.editTextProfileEmail);
        editTextHomeAddress = view.findViewById(R.id.editTextProfileHomeAddress);
        editTextPhoneNumber = view.findViewById(R.id.editTextProfilePhoneNumber);
        editTextRealName = view.findViewById(R.id.editTextProfileRealName);
        textViewUsername = view.findViewById(R.id.textViewProfileUsername);
        textViewPostNumber = view.findViewById(R.id.textViewNumberPosts);
        buttonSettings = view.findViewById(R.id.buttonSettings);
        buttonAccept = view.findViewById(R.id.buttonSettingsValid);
        Button button = view.findViewById(R.id.buttonUserPosts);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserContentDisplay();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        readData(new FirestoreCallback() {
            @Override
            public void onCallback() {
                textViewUsername.setText(userUsername);
                editTextEmail.setText(userEmail);
                textViewPostNumber.setText(userPostsNumber);
                if(!userRealName.equals("none")){
                    editTextRealName.setText(userRealName);
                }
                if(!userPhoneNumber.equals("none")){
                    editTextPhoneNumber.setText(userPhoneNumber);
                }
                if(!userHomeAddress.equals("none")){
                    editTextHomeAddress.setText(userHomeAddress);
                }

            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSettings.setVisibility(View.INVISIBLE);
                buttonSettings.setClickable(false);

                editTextHomeAddress.setFocusable(true);
                editTextHomeAddress.setFocusableInTouchMode(true);
                editTextHomeAddress.setTextIsSelectable(true);
                editTextHomeAddress.setBackgroundColor(Color.parseColor("#C7FFD2"));

                editTextPhoneNumber.setFocusable(true);
                editTextPhoneNumber.setFocusableInTouchMode(true);
                editTextPhoneNumber.setTextIsSelectable(true);
                editTextPhoneNumber.setBackgroundColor(Color.parseColor("#C7FFD2"));

                editTextRealName.setFocusable(true);
                editTextRealName.setFocusableInTouchMode(true);
                editTextRealName.setTextIsSelectable(true);
                editTextRealName.setBackgroundColor(Color.parseColor("#C7FFD2"));

                buttonAccept.setVisibility(View.VISIBLE);
                buttonAccept.setClickable(true);
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSettings.setVisibility(View.VISIBLE);
                buttonSettings.setClickable(true);

                firebaseFirestore.collection("users").document(firebaseUser.getUid()).update("phonenumber", editTextPhoneNumber.getText().toString());
                firebaseFirestore.collection("users").document(firebaseUser.getUid()).update("realname", editTextRealName.getText().toString());
                firebaseFirestore.collection("users").document(firebaseUser.getUid()).update("homeaddress", editTextHomeAddress.getText().toString());

                editTextHomeAddress.setFocusable(false);
                editTextHomeAddress.setFocusableInTouchMode(false);
                editTextHomeAddress.setTextIsSelectable(false);
                editTextHomeAddress.setBackgroundColor(Color.parseColor("#FFFFFF"));

                editTextPhoneNumber.setFocusable(false);
                editTextPhoneNumber.setFocusableInTouchMode(false);
                editTextPhoneNumber.setTextIsSelectable(false);
                editTextPhoneNumber.setBackgroundColor(Color.parseColor("#FFFFFF"));

                editTextRealName.setFocusable(false);
                editTextRealName.setFocusableInTouchMode(false);
                editTextRealName.setTextIsSelectable(false);
                editTextRealName.setBackgroundColor(Color.parseColor("#FFFFFF"));

                buttonAccept.setVisibility(View.INVISIBLE);
                buttonAccept.setClickable(false);
            }
        });

        buttonSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        imageViewProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                System.out.println("IMAGE URI"+imageUri.toString());
                imageViewProfilePicture.setImageURI(imageUri);
                imageViewProfilePicture.getLayoutParams().height = 400;
                imageViewProfilePicture.getLayoutParams().width = 400;

                uploadImageToFirebase(imageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri){

        String user = firebaseUser.getUid();
        StorageReference image = storageReference.child("images/"+user);
        image.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Image sent to Firestorage.",Toast.LENGTH_SHORT).show();
                documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
                Map<String,Object> user = new HashMap<>();
                user.put("profileimage","true");
                documentReference.set(user, SetOptions.merge());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Image failed to be sent to Firestorage.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void readData(final FirestoreCallback firestoreCallback){
        firebaseFirestore.collection("users").document(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                System.out.println("PROFILE LOADING SUCCESS");
                userUsername=documentSnapshot.getString("username");
                userRealName=documentSnapshot.getString("realname");
                userEmail=documentSnapshot.getString("email");
                userPhoneNumber=documentSnapshot.getString("phonenumber");
                userHomeAddress=documentSnapshot.getString("homeaddress");
                userProfileImage=documentSnapshot.getString("profileimage");
                userPostsNumber=documentSnapshot.get("numberposts").toString();
                if(!userProfileImage.equals("none")){
                    StorageReference image = storageReference.child("images/"+firebaseUser.getUid());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    image.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            imageViewProfilePicture.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0,bytes.length));
                            imageViewProfilePicture.getLayoutParams().height = 400;
                            imageViewProfilePicture.getLayoutParams().width = 400;
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
                firestoreCallback.onCallback();
                System.out.println("USER DATA: username"+userUsername+" realname: "+userRealName+" email: "+userEmail+" image: "+userProfileImage+" phoneadress: "+userHomeAddress+userPhoneNumber);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("PROFILE LOADING FAILED");
            }
        });

    }

    private interface FirestoreCallback{
        void onCallback();

    }
}