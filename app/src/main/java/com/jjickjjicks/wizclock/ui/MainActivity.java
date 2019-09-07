package com.jjickjjicks.wizclock.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jjickjjicks.wizclock.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_clock, R.id.navigation_alarm, R.id.navigation_timer, R.id.navigation_stopwatch)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_announce:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jjickjjicks.com/update"));
                startActivity(i);
                return true;

            case R.id.action_feedback:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jjickjjicks.com/advise"));
                startActivity(i);
                return true;

            case R.id.action_opensource:
                i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jjickjjicks.com/opensource"));
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
