package com.ppl2jt.sawadikap_java;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.ppl2jt.sawadikap_java.fragments.main.HistoryFragment;
import com.ppl2jt.sawadikap_java.fragments.main.HomeFragment;
import com.ppl2jt.sawadikap_java.fragments.main.WardrobeFragment;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    FragmentManager fm;
    BottomNavigationView bottomBar;
    Fragment active;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private TextView fullNameNav;
    private TextView usernameNav;
    private TextView emailNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nv);

        fragment1 = new HomeFragment();
        fragment2 = new WardrobeFragment();
        fragment3 = new HistoryFragment();
        active = fragment1;

        fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();

        fab = findViewById(R.id.fab);
        bottomBar = findViewById(R.id.bottomBar);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

        View navView = navigationView.getHeaderView(0);
        fullNameNav = navView.findViewById(R.id.fullNameNavBarText);
        usernameNav = navView.findViewById(R.id.usernameNavBarText);
        emailNav = navView.findViewById(R.id.emailNavBarText);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFERENCE_STORY",
                MODE_PRIVATE);

        fullNameNav.setText(sharedPreferences.getString("fullName", ""));
        usernameNav.setText(sharedPreferences.getString("username", ""));
        emailNav.setText(sharedPreferences.getString("email", ""));

        bottomBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                fm.beginTransaction().hide(active).show(fragment1).commit();
                                active = fragment1;
                                return true;

                            case R.id.wardrobe:
                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
                                return true;

                            case R.id.history:
                                fm.beginTransaction().hide(active).show(fragment3).commit();
                                active = fragment3;
                                return true;
                        }
                        return false;
                    }
                });

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit:
                                Toast.makeText(MainActivity.this, "Ubah Akun",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.log_out:
                                new AlertDialog.Builder(MainActivity.this)
                                        .setMessage("Apakah anda ingin keluar dari akun?")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(MainActivity.this,
                                                                LoginActivity.class);
                                                        finishAffinity();
                                                        getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
                                                                .edit()
                                                                .putBoolean("isLoggedIn", false).apply();
                                                        startActivity(intent);
                                                    }
                                                })
                                        .setNegativeButton("Tidak", null)
                                        .show();
                                return true;
                        }
                        return true;
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true;

        switch (item.getItemId()) {

            case R.id.account:
                Toast.makeText(MainActivity.this, "Tombol akun ditekan",
                        Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.END);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
