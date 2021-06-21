package reytax.project.eventapp.menuscreen.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import reytax.project.eventapp.R;
import reytax.project.eventapp.authentication.MainActivity;
import reytax.project.eventapp.menuscreen.MenuScreenActivity;
import reytax.project.eventapp.menuscreen.profile.ProfileActivity;
import reytax.project.eventapp.menuscreen.profile.UserSettingsActivity;
import reytax.project.eventapp.utils.firebase.FirebaseInit;

public class NavigationBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        context = this;

        firebaseAuth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.activity_menu_screen_drawerLayout);
        navigationView = findViewById(R.id.activity_menu_screen_navigationView);
        toolbar = findViewById(R.id.activity_menu_screen_toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationIcon(R.drawable.menu);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.side_menu_home:
                if (getClass() != MenuScreenActivity.class) {
                    startActivity(new Intent(getApplicationContext(), MenuScreenActivity.class));
                }
                drawerLayout.closeDrawers();
                break;
            case R.id.side_menu_profile:
                if (getClass() != ProfileActivity.class) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.putExtra("userId", FirebaseInit.getFirebaseUser().getUid());
                    startActivity(intent);
                }
                drawerLayout.closeDrawers();
                break;
            case R.id.side_menu_settings:
                if (getClass() != UserSettingsActivity.class) {
                    startActivity(new Intent(getApplicationContext(), UserSettingsActivity.class));
                }
                drawerLayout.closeDrawers();
                break;
            case R.id.side_menu_singout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                drawerLayout.closeDrawers();
                break;

        }

        return true;
    }
}