package reytax.project.eventapp.menuscreen.calendar;

import android.app.ActionBar;
import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import reytax.project.eventapp.R;
import reytax.project.eventapp.utils.firebase.FirebaseInitialization;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarTwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarTwoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarTwoActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarTwoFragment newInstance(String param1, String param2) {
        CalendarTwoFragment fragment = new CalendarTwoFragment();
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

    private CompactCalendarView compactCalendarView;
    private LinearLayout linearLayout;
    private TextView textViewMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar_two, container, false);
        compactCalendarView = view.findViewById(R.id.fragment_calendar_two_compactCalendarView);
        linearLayout = view.findViewById(R.id.fragment_calendar_two_linearLayout);
        textViewMonth = view.findViewById(R.id.fragment_calendar_two_textViewMonth);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM-yyyy", Locale.getDefault());
        textViewMonth.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("users").document(FirebaseInitialization.getFirebaseUser().getUid()).collection("events");

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot document : documents) {


                    List<Date> dates = getDates(document.get("dateStart").toString(),document.get("dateEnd").toString());

                    for(Date date:dates){
                        long milliTime = date.getTime();
                        Event event = new Event(Color.GREEN, milliTime, document.get("title"));
                        compactCalendarView.addEvent(event);
                    }

                }

                compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                    @Override
                    public void onDayClick(Date dateClicked) {
                        List<Event> events = compactCalendarView.getEvents(dateClicked);
                        linearLayout.removeAllViews();
                        for(Event event:events){
                            System.out.println(event.getData().toString());

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            layoutParams.gravity = 17;
                            layoutParams.topMargin = 30;
                            layoutParams.bottomMargin = 30;

                            TextView textView = new TextView(getContext());
                            textView.setBackgroundResource(R.color.bright_grey);
                            textView.setTextSize(18);
                            textView.setLayoutParams(layoutParams);
                            textView.setText(event.getData().toString());
                            linearLayout.addView(textView);

                            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                            layoutParams.height = 5;

                            View view = new View(getContext());
                            view.setBackgroundResource(R.color.EventGreen);
                            view.setLayoutParams(layoutParams);
                            linearLayout.addView(view);
                        }

                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {

                        textViewMonth.setText(simpleDateFormat.format(firstDayOfNewMonth));
                    }
                });
            }



        });

        return view;
    }

    private List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

}