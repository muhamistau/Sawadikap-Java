package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ppl2jt.sawadikap_java.job.Checker;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText emailEditText;
    TextInputLayout emailInputLayout;
    Button emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_email);

        emailEditText = findViewById(R.id.inputEmail);
        emailInputLayout = findViewById(R.id.layoutEmail);
        emailButton = findViewById(R.id.buttonLanjutEmail);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText().toString().equals("")) {
                    emailInputLayout.setError("Tidak boleh kosong");
                } else if (!Checker.isEmailValid(emailEditText.getText().toString())) {
                    emailInputLayout.setError("Alamat email tidak valid");
                } else {
                    Intent intent = new Intent(SignupActivity.this, SignupActivity2.class);
                    intent.putExtra("email", emailEditText.getText().toString());
                    Log.d("TESTEmail", emailEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
