package com.ppl2jt.sawadikap_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        text = findViewById(R.id.textView);
        text.setText("" + intent.getStringExtra("email") + "\n"
                + intent.getStringExtra("password"));
    }
}
