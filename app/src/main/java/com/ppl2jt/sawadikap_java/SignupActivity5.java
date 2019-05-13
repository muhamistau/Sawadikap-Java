package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity5 extends AppCompatActivity {

    TextInputEditText usernameEditText;
    TextInputLayout usernameInputLayout;
    Button usernameButton;
    String email;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_username);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        usernameEditText = findViewById(R.id.inputUsername);
        usernameButton = findViewById(R.id.buttonLanjutUsername);

        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().toString().equals("")) {
                    usernameInputLayout.setError("Alamat tidak boleh kosong");
                } else {
                    Intent intent = new Intent(SignupActivity5.this, SignupActivity3.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("username", usernameEditText.getText().toString());
//                    Log.d("TESTemail", email);
//                    Log.d("TESTname", name);
//                    Log.d("TESTname", usernameEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
