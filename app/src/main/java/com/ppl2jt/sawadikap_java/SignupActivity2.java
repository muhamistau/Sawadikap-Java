package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity2 extends AppCompatActivity {

    TextInputEditText nameEditText;
    TextInputLayout nameInputLayout;
    Button nameButton;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_full_name);

        email = getIntent().getStringExtra("email");

        nameEditText = findViewById(R.id.inputName);
        nameInputLayout = findViewById(R.id.layoutFullName);
        nameButton = findViewById(R.id.buttonLanjutName);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().equals("")) {
                    nameInputLayout.setError("Nama tidak boleh kosong");
                } else {
                    Intent intent = new Intent(SignupActivity2.this, SignupActivity5.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", nameEditText.getText().toString());
                    Log.d("TESTemail", email);
                    Log.d("TESTname", nameEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
