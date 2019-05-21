package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity4 extends AppCompatActivity {

    TextInputEditText passwordEditText;
    TextInputLayout passwordInputLayout;
    Button passwordButton;
    String email;
    String name;
    String username;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_password);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        address = getIntent().getStringExtra("address");

        passwordEditText = findViewById(R.id.inputPassword);
        passwordInputLayout = findViewById(R.id.layoutPassword);
        passwordButton = findViewById(R.id.buttonLanjutPassword);

        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.getText().toString().equals("")) {
                    passwordInputLayout.setError("Password tidak boleh kosong");
                } else if (passwordEditText.getText().toString().length() < 8) {
                    passwordInputLayout.setError("Password minimal 8 karakter");
                } else {
                    Intent intent = new Intent(SignupActivity4.this,
                            SignupConfirmationActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("username", username);
                    intent.putExtra("address", address);
                    intent.putExtra("password", passwordEditText.getText().toString());

                    finishAffinity();
                    startActivity(intent);
                }
            }
        });
    }
}
