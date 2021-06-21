package reytax.project.eventapp.menuscreen;

import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.event.create.CreateEventActivity;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;

public class MenuScreenActivity extends NavigationBarActivity {

    private CardView cardView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_menu_screen, contentFrameLayout);

        cardView = findViewById(R.id.activity_menu_screen_cardViewAddEvent);

        context = this;

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CreateEventActivity.class));
                finish();
            }
        });
    }
}