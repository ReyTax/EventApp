package reytax.project.eventapp.menuscreen.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.friendlist.FriendListFragment;
import reytax.project.eventapp.menuscreen.user.friendlist.FriendListPendingFragment;

public class CalendarActivity extends NavigationBarActivity {

    private Button buttonCalendar, buttonParticipation;
    private int currentFragment = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_navigation_bar_frameLayout);
        getLayoutInflater().inflate(R.layout.activity_calendar, contentFrameLayout);

        buttonParticipation = findViewById(R.id.activity_calendar_buttonParticipation);
        buttonCalendar = findViewById(R.id.activity_calendar_buttonCalendar);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_calendar_frameLayout, new CalendarOneFragment()).commit();


        buttonParticipation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment == 2) {
                    currentFragment = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_calendar_frameLayout, new CalendarOneFragment()).commit();
                }

            }
        });

        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment == 1) {
                    currentFragment = 2;
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_calendar_frameLayout, new CalendarTwoFragment()).commit();
                }
            }
        });
    }
}