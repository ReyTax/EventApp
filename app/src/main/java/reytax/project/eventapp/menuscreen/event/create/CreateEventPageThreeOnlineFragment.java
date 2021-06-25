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
 * Use the {@link CreateEventPageThreeOnlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventPageThreeOnlineFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventPageThreeOnlineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventPageThreeOnlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventPageThreeOnlineFragment newInstance(String param1, String param2) {
        CreateEventPageThreeOnlineFragment fragment = new CreateEventPageThreeOnlineFragment();
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

    private EditText editTextContact, editTextDateStart, editTextDateEnd;
    private String date;
    private int dateOption;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event_page_three_online, container, false);

        editTextContact = view.findViewById(R.id.fragment_create_event_page_three_online_editTextContact);
        editTextDateStart = view.findViewById(R.id.fragment_create_event_page_three_online_editTextDateStart);
        editTextDateEnd = view.findViewById(R.id.fragment_create_event_page_three_online_editTextDateEnd);

        editTextContact.setText(EventUploadManager.getContact());
        editTextDateStart.setText(EventUploadManager.getDateStart());
        editTextDateEnd.setText(EventUploadManager.getDateEnd());
        editTextDateStart.setKeyListener(null);
        editTextDateEnd.setKeyListener(null);

        editTextDateStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    showDatePickerDialog(1);
                return false;
            }
        });

        editTextDateEnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    showDatePickerDialog(2);
                return false;
            }
        });

        return view;
    }

    public void onDestroy() {
        EventUploadManager.setDataThirdFragment("", "", "", "", editTextContact.getText().toString(), editTextDateStart.getText().toString(), editTextDateEnd.getText().toString());
        super.onDestroy();
    }

    private void showDatePickerDialog(int value) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        dateOption = value;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = null;
        date = dayOfMonth + "/" + month + "/" + year;
        if(dateOption == 1){
            editTextDateStart.setText(date);
        }
        else {
            editTextDateEnd.setText(date);
        }
    }
}