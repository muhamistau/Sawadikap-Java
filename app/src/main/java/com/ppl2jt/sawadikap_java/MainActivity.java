package com.ppl2jt.sawadikap_java;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        BottomAppBar bar = findViewById(R.id.bottomBar);
//        setSupportActionBar(bar);

//        Intent intent = getIntent();

//        text = findViewById(R.id.textView);
//        text.setText("" + intent.getStringExtra("email") + "\n"
//                + intent.getStringExtra("username") + intent.getStringExtra("picUrl"));
    }
}
