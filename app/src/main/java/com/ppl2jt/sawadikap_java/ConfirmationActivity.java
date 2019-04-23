package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {

    ImageView picture;
    TextView age, category, gender, size, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        picture = findViewById(R.id.imageView);
        age = findViewById(R.id.age);
        category = findViewById(R.id.category);
        gender = findViewById(R.id.gender);
        size = findViewById(R.id.size);
        rating = findViewById(R.id.rating);

        Intent intent = getIntent();

        picture.setImageURI(Uri.parse(intent.getStringExtra("image")));
        age.setText("Usia: " + intent.getStringExtra("age"));
        category.setText("Jenis Pakaian: " + intent.getStringExtra("category"));
        gender.setText("Jenis Kelamin: " + intent.getStringExtra("gender"));
        size.setText("Ukuran: " + intent.getStringExtra("size"));
        rating.setText("Kondisi: " + intent.getStringExtra("rating"));
    }

    public void done(View view) {

    }
}
