package com.example.eventapp.mainscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.eventapp.R;
import com.example.eventapp.mainscreen.fragments.CreateFragment;
import com.example.eventapp.mainscreen.fragments.ProfileFragment;
import com.example.eventapp.mainscreen.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private  BottomNavigationView selectedNav;
    private int navId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        selectedNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        selectedNav.setOnNavigationItemSelectedListener(this);
        selectedNav.getMenu().findItem(R.id.nav_profile).setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // uncheck the other items.
        navId = item.getItemId();
        for (int i = 0; i < selectedNav.getMenu().size(); i++) {
            MenuItem menuItem = selectedNav.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_profile: {
                selectedFragment=new ProfileFragment();
            }
            break;
            case R.id.nav_search: {
                selectedFragment=new SearchFragment();
            }
            break;
            case R.id.nav_create: {
                selectedFragment=new CreateFragment();
            }
            break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        return true;
    }
}