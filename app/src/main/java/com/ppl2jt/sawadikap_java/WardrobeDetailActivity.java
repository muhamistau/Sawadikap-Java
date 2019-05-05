package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class WardrobeDetailActivity extends AppCompatActivity {

    ImageView picture;
    TextView type;
    TextView age;
    TextView size;
    TextView condition;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe_detail);

        picture = findViewById(R.id.fotoPakaian);
        type = findViewById(R.id.tipe);
        age = findViewById(R.id.usia);
        size = findViewById(R.id.ukuran);
        condition = findViewById(R.id.kondisi);
        status = findViewById(R.id.status);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("foto")).into(picture);
        type.setText(intent.getStringExtra("jenis_baju"));
        age.setText(intent.getStringExtra("jenis_usia"));
        size.setText(intent.getStringExtra("jenis_ukuran"));
        condition.setText(intent.getStringExtra("status"));
        status.setText("Belum Dibuat");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void backPressed(View view) {
        onBackPressed();
    }
}
