package reytax.project.eventapp.menuscreen.event.create;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.InputStream;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;
import reytax.project.eventapp.utils.firebase.EventUploadManager;
import reytax.project.eventapp.utils.firebase.UserDataManager;

public class CreateEventActivity extends NavigationBarActivity {

    private Button buttonBack, buttonNext;
    private int currentFragment;
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.activity_navigation_bar_frameLayout);
        getLayoutInflater().inflate(R.layout.activity_create_event, contentFrameLayout);

        EventUploadManager.initialization();

        buttonBack = findViewById(R.id.activity_create_event_buttonBack);
        buttonNext = findViewById(R.id.activity_create_event_buttonNext);
        view1 = findViewById(R.id.activity_create_event_view1);
        view2 = findViewById(R.id.activity_create_event_view2);
        view3 = findViewById(R.id.activity_create_event_view3);

        currentFragment = 1;
        setIndicatorIcon(currentFragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageOneFragment()).commit();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentFragment) {
                    case 1:
                        currentFragment = 2;
                        setIndicatorIcon(currentFragment);
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageTwoFragment()).commit();
                        break;
                    case 2:
                        currentFragment = 3;
                        setIndicatorIcon(currentFragment);
                        if (EventUploadManager.getEventType().equals("onsite")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageThreeOnsiteFragment()).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageThreeOnlineFragment()).commit();
                        }
                        break;
                    case 3:
                        currentFragment = 4;
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageFourFragment()).commit();
                        break;
                    case 4:
                        EventUploadManager.uploadEventToFirebase();
                        UserDataManager.incrementEventsCountLocal();
                        finish();
                        break;
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentFragment) {
                    case 1:
                        finish();
                        break;
                    case 2:
                        currentFragment = 1;
                        setIndicatorIcon(currentFragment);
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageOneFragment()).commit();
                        break;
                    case 3:
                        currentFragment = 2;
                        setIndicatorIcon(currentFragment);
                        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageTwoFragment()).commit();
                        break;
                    case 4:
                        currentFragment = 3;
                        setIndicatorIcon(currentFragment);
                        if (EventUploadManager.getEventType().equals("onsite")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageThreeOnsiteFragment()).commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_event_frameLayout, new CreateEventPageThreeOnlineFragment()).commit();
                        }
                        break;
                }
            }
        });

    }

    public void setIndicatorIcon(int value) {
        switch (value) {
            case 1:
                view1.setBackground(getDrawable(R.drawable.ic_dot_indicator_enabled));
                view2.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                view3.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                break;
            case 2:
                view1.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                view2.setBackground(getDrawable(R.drawable.ic_dot_indicator_enabled));
                view3.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                break;
            case 3:
                view1.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                view2.setBackground(getDrawable(R.drawable.ic_dot_indicator_disabled));
                view3.setBackground(getDrawable(R.drawable.ic_dot_indicator_enabled));
                break;
        }
    }
}