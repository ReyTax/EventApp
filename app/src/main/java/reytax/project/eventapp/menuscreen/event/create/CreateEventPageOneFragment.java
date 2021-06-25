package reytax.project.eventapp.menuscreen.event.create;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.activity.InputFilterParticipant;
import reytax.project.eventapp.utils.firebase.EventUploadManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventPageOneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPageOneFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPageOneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventPageOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPageOneFragment newInstance(String param1, String param2) {
        CreateEventPageOneFragment fragment = new CreateEventPageOneFragment();
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

    private EditText editTextParticipantsNumber, editTextTitle;
    private CardView cardViewOnline, cardViewOnsite;
    private String eventType = "";
    private ImageView imageViewOnline, imageViewOnsite;
    private TextView textViewOnline, textViewOnsite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_page_one, container, false);

        editTextParticipantsNumber = view.findViewById(R.id.fragment_create_event_page_one_editTextParticipantsNumber);
        editTextTitle = view.findViewById(R.id.fragment_create_event_page_one_editTextTitle);
        cardViewOnline = view.findViewById(R.id.fragment_create_event_page_one_cardViewOnline);
        cardViewOnsite = view.findViewById(R.id.fragment_create_event_page_one_cardViewOnsite);
        imageViewOnline = view.findViewById(R.id.fragment_create_event_page_one_imageViewOnline);
        imageViewOnsite = view.findViewById(R.id.fragment_create_event_page_one_imageViewOnsite);
        textViewOnline = view.findViewById(R.id.fragment_create_event_page_one_textViewOnline);
        textViewOnsite = view.findViewById(R.id.fragment_create_event_page_one_textViewOnsite);

        imageViewOnline.setImageResource(R.drawable.ic_online_disabled);
        imageViewOnsite.setImageResource(R.drawable.ic_onsite_disabled);
        textViewOnline.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        textViewOnsite.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));

        editTextParticipantsNumber.setFilters(new InputFilter[]{new InputFilterParticipant()});

        editTextTitle.setText(EventUploadManager.getTitle());
        editTextParticipantsNumber.setText(EventUploadManager.getParticipantsNumber());
        if (EventUploadManager.getEventType().equals("online")) {
            setOnline();
        }
        if (EventUploadManager.getEventType().equals("onsite")) {
            setOnsite();
        }

        editTextParticipantsNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textChange();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChange();
            }

            @Override
            public void afterTextChanged(Editable s) {
                textChange();
            }
        });


        cardViewOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnline();
            }
        });

        cardViewOnsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnsite();
            }
        });


        return view;
    }

    private void setOnline() {
        eventType = "online";
        imageViewOnline.setImageResource(R.drawable.ic_online_enabled);
        imageViewOnsite.setImageResource(R.drawable.ic_onsite_disabled);
        textViewOnline.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        textViewOnsite.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
    }

    private void setOnsite() {
        eventType = "onsite";
        imageViewOnline.setImageResource(R.drawable.ic_online_disabled);
        imageViewOnsite.setImageResource(R.drawable.ic_onsite_enabled);
        textViewOnline.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
        textViewOnsite.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
    }

    private void textChange() {

        if (!editTextParticipantsNumber.getText().toString().equals("")) {
            if (Integer.parseInt(editTextParticipantsNumber.getText().toString()) < 2) {
                Thread thread = new Thread() {

                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!editTextParticipantsNumber.getText().toString().equals("")) {
                                    if (Integer.parseInt(editTextParticipantsNumber.getText().toString()) < 2) {
                                        editTextParticipantsNumber.setText("2");
                                    }
                                }
                            }
                        });
                    }
                };
                thread.start();
            }
        }
    }



    @Override
    public void onDestroy() {
        EventUploadManager.setDataFirstFragment(eventType, editTextTitle.getText().toString(), editTextParticipantsNumber.getText().toString());
        super.onDestroy();
    }
}