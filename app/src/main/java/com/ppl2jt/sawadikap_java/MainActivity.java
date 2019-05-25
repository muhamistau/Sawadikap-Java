package com.ppl2jt.sawadikap_java;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 100;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 1002;

    FloatingActionButton fab, fab_camera, fab_gallery;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    FragmentManager fm;
    BottomNavigationView bottomBar;
    Fragment active;
    Uri imageUri;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private TextView fullNameNav;
    private TextView usernameNav;
    private TextView emailNav;
    private Boolean fabIsOpen = false;
    private TextView cameraTextView;
    private TextView galleryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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
        fab_camera = findViewById(R.id.fab1);
        fab_gallery = findViewById(R.id.fab2);

        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fab_rotate_anticlock);

        cameraTextView = findViewById(R.id.textview_camera);
        galleryTextView = findViewById(R.id.textview_gallery);

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
                                closeFab();
                                return true;

                            case R.id.wardrobe:
                                fm.beginTransaction().hide(active).show(fragment2).commit();
                                active = fragment2;
                                closeFab();
                                return true;

                            case R.id.history:
                                fm.beginTransaction().hide(active).show(fragment3).commit();
                                active = fragment3;
                                closeFab();
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
                                Intent intent = new Intent(MainActivity.this,
                                        EditProfileActivity.class);
                                startActivity(intent);
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
//                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
//                startActivity(intent);
                if (fabIsOpen) {
                    closeFab();
                } else {
                    openFab();
                }
            }
        });

        fab_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED
                            && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, IMAGE_CAPTURE_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                    imageUri = getContentResolver()
                            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
                }
            }
        });

        fab_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
            }
        });

    }

    private void openFab() {
        cameraTextView.setVisibility(View.VISIBLE);
        galleryTextView.setVisibility(View.VISIBLE);
        fab_camera.show();
        fab_gallery.show();
        fab_camera.startAnimation(fab_open);
        fab_gallery.startAnimation(fab_open);
        fab.startAnimation(fab_clock);
        fab_camera.setClickable(true);
        fab_gallery.setClickable(true);
        fabIsOpen = true;
    }

    private void closeFab() {
        if (fabIsOpen) {
            cameraTextView.setVisibility(View.GONE);
            galleryTextView.setVisibility(View.GONE);
            fab_camera.hide();
            fab_gallery.hide();
            fab_camera.startAnimation(fab_close);
            fab_gallery.startAnimation(fab_close);
            fab.startAnimation(fab_anticlock);
            fab_camera.setClickable(false);
            fab_gallery.setClickable(false);
            fabIsOpen = false;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                imageUri = data.getData();
                Intent intentGallery = new Intent(MainActivity.this,
                        CategoryActivity.class);
                intentGallery.putExtra("image", imageUri.toString());
                startActivity(intentGallery);
                break;

            case CAMERA_REQUEST:
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                imageUri = getImageUri(this, photo);
                Intent intentCamera = new Intent(MainActivity.this,
                        CategoryActivity.class);
                intentCamera.putExtra("image", imageUri.toString());
                startActivity(intentCamera);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
                "Title", null);
        return Uri.parse(path);
    }

    @Override
    protected void onResume() {
        super.onResume();
        closeFab();
    }
}
