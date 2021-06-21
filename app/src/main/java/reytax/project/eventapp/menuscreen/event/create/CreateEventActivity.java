package reytax.project.eventapp.menuscreen.event.create;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import reytax.project.eventapp.R;
import reytax.project.eventapp.menuscreen.navigation.NavigationBarActivity;

public class CreateEventActivity extends NavigationBarActivity {

    private LayoutInflater view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_create_event, contentFrameLayout);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateEventPageOneFragment()).commit();

    }
}