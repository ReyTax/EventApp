package reytax.project.eventapp.menuscreen;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SearchEvent;
import android.view.View;
import android.widget.FrameLayout;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.create.CreateEventActivity;
import reytax.project.eventapp.menuscreen.event.search.SearchEventActivity;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.menuscreen.user.search.SearchUserActivity;

public class MenuScreenActivity extends NavigationBarActivity {

    private CardView cardViewCreateEvent, cardViewSearchEvent, cardViewSearchUser;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_menu_screen, contentFrameLayout);

        cardViewCreateEvent = findViewById(R.id.activity_menu_screen_cardViewAddEvent);
        cardViewSearchEvent = findViewById(R.id.activity_menu_screen_cardViewSearchEvent);
        cardViewSearchUser = findViewById(R.id.activity_menu_screen_cardViewSearchUser);

        context = this;

        cardViewCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateEventActivity.class));
            }
        });

        cardViewSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchEventActivity.class));
            }
        });

        cardViewSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SearchUserActivity.class));
            }
        });
    }
}