package reytax.project.eventapp.menuscreen.event.create;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.firebase.EventUploadManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventPageTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPageTwoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPageTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventPageTwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPageTwoFragment newInstance(String param1, String param2) {
        CreateEventPageTwoFragment fragment = new CreateEventPageTwoFragment();
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

    private EditText editTextDescription;
    private TextView textViewCharactersLimit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_page_two, container, false);

        editTextDescription = view.findViewById(R.id.fragment_create_event_page_two_editTextDescription);
        textViewCharactersLimit = view.findViewById(R.id.fragment_create_event_page_two_textViewCharactersLimit);

        editTextDescription.setText(EventUploadManager.getDescription());

        textViewCharactersLimit.setText(editTextDescription.length() + "/1500");

        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textViewCharactersLimit.setText(s.length() + "/1500");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        EventUploadManager.setDataSecondFragment(editTextDescription.getText().toString());
        super.onDestroy();
    }
}