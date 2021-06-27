package reytax.project.eventapp.menuscreen;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.calendar.CalendarActivity;
import reytax.project.eventapp.menuscreen.event.create.CreateEventActivity;
import reytax.project.eventapp.menuscreen.event.search.SearchEventActivity;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.friendlist.FriendListActivity;
import reytax.project.eventapp.menuscreen.user.search.SearchUserActivity;

public class MenuScreenActivity extends NavigationBarActivity {

    private CardView cardViewCreateEvent, cardViewSearchEvent, cardViewSearchUser, cardViewFriendList , cardViewCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_menu_screen, contentFrameLayout);

        cardViewCreateEvent = findViewById(R.id.activity_menu_screen_cardViewAddEvent);
        cardViewSearchEvent = findViewById(R.id.activity_menu_screen_cardViewSearchEvent);
        cardViewSearchUser = findViewById(R.id.activity_menu_screen_cardViewSearchUser);
        cardViewFriendList = findViewById(R.id.activity_menu_screen_cardViewFriendList);
        cardViewCalendar = findViewById(R.id.activity_menu_screen_cardViewCalendar);

        cardViewCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cardViewSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchEventActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cardViewSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchUserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cardViewFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cardViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}