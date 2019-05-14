package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ppl2jt.sawadikap_java.fragments.main.HistoryFragment;
import com.ppl2jt.sawadikap_java.fragments.main.HomeFragment;
import com.ppl2jt.sawadikap_java.fragments.main.WardrobeFragment;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new WardrobeFragment();
    final Fragment fragment3 = new HistoryFragment();
    final FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView bottomBar;
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();

        fab = findViewById(R.id.fab);
        bottomBar = findViewById(R.id.bottomBar);
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

//        Intent intent = getIntent();

//        text = findViewById(R.id.textView);
//        text.setText("" + intent.getStringExtra("email") + "\n"
//                + intent.getStringExtra("username") + intent.getStringExtra("picUrl"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE).edit()
                        .putBoolean("isLoggedIn", false).apply();
                startActivity(intent);
                return true;
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
