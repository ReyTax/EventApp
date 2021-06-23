package reytax.project.eventapp.menuscreen.event.create;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.firebase.EventUploadManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventPageThreeOnsiteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPageThreeOnsiteFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPageThreeOnsiteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventPageThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPageThreeOnsiteFragment newInstance(String param1, String param2) {
        CreateEventPageThreeOnsiteFragment fragment = new CreateEventPageThreeOnsiteFragment();
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

    private EditText editTextCountry, editTextCity, editTextAddress, editTextContact, editTextDate;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_page_three_onsite, container, false);

        editTextCountry = view.findViewById(R.id.fragment_create_event_page_three_onsite_editTextCountry);
        editTextCity = view.findViewById(R.id.fragment_create_event_page_three_onsite_editTextCity);
        editTextAddress = view.findViewById(R.id.fragment_create_event_page_three_onsite_editTextAddress);
        editTextContact = view.findViewById(R.id.fragment_create_event_page_three_onsite_editTextContact);
        editTextDate = view.findViewById(R.id.fragment_create_event_page_three_onsite_editTextDate);

        editTextCountry.setText(EventUploadManager.getCountry());
        editTextCity.setText(EventUploadManager.getCity());
        editTextAddress.setText(EventUploadManager.getAddress());
        editTextContact.setText(EventUploadManager.getContact());
        editTextDate.setText(EventUploadManager.getDate());
        editTextDate.setKeyListener(null);

        editTextDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                    showDatePickerDialog();
                return false;
            }
        });

        return view;
    }

    public void onDestroy() {
        EventUploadManager.setDataThirdFragment(editTextCountry.getText().toString(), editTextCity.getText().toString(), editTextAddress.getText().toString(), editTextContact.getText().toString(), editTextDate.getText().toString());
        super.onDestroy();
    }

    public void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),this, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        editTextDate.setText(date);
    }
}