package com.example.eventapp.mainscreen.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eventapp.R;
import com.example.eventapp.utility.SpanTypeface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserContentDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserContentDisplay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LinearLayout linearLayout;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    String userID;
    CollectionReference colRefAll;
    List<String> announcementTitle = new ArrayList<String>() , announcementContent = new ArrayList<String>(), announcementAddress = new ArrayList<String>();
    List<Integer> textViewId = new ArrayList<Integer>();
    TextView textViewAux;
    boolean selectState=false;
    int lastSelected;
    String textFormat="";

    public UserContentDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserContentDisplay.
     */
    // TODO: Rename and change types and number of parameters
    public static UserContentDisplay newInstance(String param1, String param2) {
        UserContentDisplay fragment = new UserContentDisplay();
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
        View view = inflater.inflate(R.layout.fragment_user_content_display, container, false);
        linearLayout = view.findViewById(R.id.linearLayoutUserContent);

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseUser.getUid();
        colRefAll = firebaseFirestore.collection("userAnnouncements").document(userID).collection("announcements");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        readData(new FirestoreCallback() {
            @Override
            public void onCallback() {
                for(int i = 1; i<= announcementTitle.size(); i++){
                    textFormat = announcementTitle.get(i-1);
                    //textFormat = textFormat.substring(textFormat.lastIndexOf("text="),textFormat.length()-1);
                    final TextView textView = new TextView(getActivity());
                    textView.setTextColor(Color.parseColor("#FF000000"));
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_open, 0);
                    textView.setPadding(40,20,40,20);
                    textView.setBackgroundResource(R.drawable.textboxshape);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    if(textFormat!=null){
                        SpannableString spannableString=  new SpannableString(textFormat);
                        spannableString.setSpan(new RelativeSizeSpan(2f),0,textFormat.length(),0);
                        spannableString.setSpan(new SpanTypeface(ResourcesCompat.getFont(getActivity(),R.font.roundarial)),0,textFormat.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, textFormat.length(), 0);
                        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
                    }
                    else
                        textView.setText(textFormat);

                    linearLayout.addView(textView);
                    textView.setId(View.generateViewId());
                    textViewId.add(textView.getId());

                    textView.setOnClickListener(new View.OnClickListener() {



                        @Override
                        public void onClick(View v) {
                            System.out.println("Test : "+1);
                            String stringTitle,stringContent,stringAddress;


                            if(selectState==false){
                                selectState=true;
                                lastSelected=textView.getId();
                                stringTitle=announcementTitle.get(textViewId.indexOf(lastSelected));
                                stringContent=announcementContent.get(textViewId.indexOf(lastSelected));
                                stringAddress=announcementAddress.get(textViewId.indexOf(lastSelected));
                                if(stringTitle!=null && stringContent!=null && stringAddress!=null){
                                    textView.setText(textFormat(stringTitle,stringContent,stringAddress,1), TextView.BufferType.SPANNABLE);
                                }
                                else
                                    textView.setText(announcementTitle.get(textViewId.indexOf(lastSelected))+"\n"+announcementContent.get(textViewId.indexOf(lastSelected))+"\n"+announcementAddress.get(textViewId.indexOf(lastSelected)));
                                System.out.println("Test : "+2);
                            }
                            else
                            {
                                if(selectState==true && lastSelected==textView.getId()){
                                    stringTitle=announcementTitle.get(textViewId.indexOf(lastSelected));
                                    stringContent=announcementContent.get(textViewId.indexOf(lastSelected));
                                    stringAddress=announcementAddress.get(textViewId.indexOf(lastSelected));

                                    textView.setText(textFormat(stringTitle,stringContent,stringAddress,2), TextView.BufferType.SPANNABLE);
                                    selectState=false;
                                    System.out.println("Test : "+3);
                                }
                                else{
                                    if(selectState==true && lastSelected!=textView.getId()){

                                        stringTitle=announcementTitle.get(textViewId.indexOf(lastSelected));
                                        stringContent=announcementContent.get(textViewId.indexOf(lastSelected));
                                        stringAddress=announcementAddress.get(textViewId.indexOf(lastSelected));
                                        textViewAux = getView().findViewById(lastSelected);
                                        textViewAux.setText(textFormat(stringTitle,stringContent,stringAddress,2), TextView.BufferType.SPANNABLE);

                                        lastSelected=textView.getId();
                                        stringTitle=announcementTitle.get(textViewId.indexOf(lastSelected));
                                        stringContent=announcementContent.get(textViewId.indexOf(lastSelected));
                                        stringAddress=announcementAddress.get(textViewId.indexOf(lastSelected));

                                        if(stringTitle!=null && stringContent!=null && stringAddress!=null){
                                            textView.setText(textFormat(stringTitle,stringContent,stringAddress,1), TextView.BufferType.SPANNABLE);
                                        }
                                        else
                                            textView.setText(announcementTitle.get(textViewId.indexOf(lastSelected))+"\n"+announcementContent.get(textViewId.indexOf(lastSelected))+"\n"+announcementAddress.get(textViewId.indexOf(lastSelected)));
                                        System.out.println("Test : "+4);
                                    }
                                }
                            }
                        }
                    });
                }

                TextView textViewBottom = new TextView(getContext());
                textViewBottom.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,140));
                textViewBottom.setBackgroundColor(Color.parseColor("#48FB6B"));
                linearLayout.addView(textViewBottom);
                System.out.println( "+-+-+-++-+-+-++-+-+-++-+-+-++-+-+-++-+-+-++-+-+-+" + announcementTitle.size());
                System.out.println( "+-+-+-++-+-+-++-+-+-++-+-+-++-+-+-++-+-+-++-+-+-+" + announcementTitle.size());
            }

        });
    }

    private void readData(final FirestoreCallback firestoreCallback){
        firebaseFirestore.collection("userAnnouncements").document(firebaseUser.getUid()).collection("announcements").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d(TAG, document.getId() + " => " + document.getData());
                        announcementTitle.add(document.getString("title"));
                        announcementContent.add(document.getString("content"));
                        announcementAddress.add(document.getString("address"));
                        //System.out.println("show " + announcementText.get(announcementText.size()-1) + "   size: " + announcementText.size());
                    }
                    firestoreCallback.onCallback();
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private interface FirestoreCallback{
        void onCallback();

    }

    private CharSequence textFormat(String stringTitle, String stringContent, String stringAddress, int state){

        stringContent="\n\n"+stringContent+"\n\n";
        stringAddress=stringAddress+"\n";
        String contactAddress="Contact: ";

        SpannableString spannableTitle =  new SpannableString(stringTitle);
        spannableTitle.setSpan(new RelativeSizeSpan(2f),0,stringTitle.length(),0);
        spannableTitle.setSpan(new SpanTypeface(ResourcesCompat.getFont(getActivity(),R.font.roundarial)),0,stringTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, stringTitle.length(), 0);
        SpannableString spannableContent =  new SpannableString(stringContent);
        spannableContent.setSpan(new RelativeSizeSpan(1.2f),0,stringContent.length(),0);
        spannableContent.setSpan(new SpanTypeface(ResourcesCompat.getFont(getActivity(),R.font.roundarial)),0,stringContent.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableContent.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, stringContent.length(), 0);
        SpannableString spannableAddress =  new SpannableString(stringAddress);
        spannableAddress.setSpan(new RelativeSizeSpan(1.3f),0,stringAddress.length(),0);
        spannableAddress.setSpan(new SpanTypeface(ResourcesCompat.getFont(getActivity(),R.font.roundarial)),0,stringAddress.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableAddress.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, stringAddress.length(), 0);
        SpannableString spannableContactAddress = new SpannableString(contactAddress);
        spannableContactAddress.setSpan(new RelativeSizeSpan(1.7f),0,contactAddress.length(),0);
        spannableContactAddress.setSpan(new SpanTypeface(ResourcesCompat.getFont(getActivity(),R.font.roundarial)),0,contactAddress.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableContactAddress.setSpan(new ForegroundColorSpan(Color.parseColor("#3de35d")), 0, contactAddress.length(), 0);

        if(state==1)
            return TextUtils.concat(spannableTitle,spannableContent,spannableContactAddress,spannableAddress);

        return spannableTitle;
    }
}