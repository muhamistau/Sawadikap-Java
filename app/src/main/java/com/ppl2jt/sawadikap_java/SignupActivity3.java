package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignupActivity3 extends AppCompatActivity {

    TextInputEditText addressEditText;
    TextInputLayout addressInputLayout;
    Button addressButton;
    String email;
    String name;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_address);

        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");

        addressEditText = findViewById(R.id.inputAlamat);
        addressButton = findViewById(R.id.buttonLanjutAddress);

        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressEditText.getText().toString().equals("")) {
                    addressInputLayout.setError("Alamat tidak boleh kosong");
                } else {
                    Intent intent = new Intent(SignupActivity3.this, SignupActivity4.class);
                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    intent.putExtra("username", username);
                    intent.putExtra("address", addressEditText.getText().toString());
//                    Log.d("TESTemail", email);
//                    Log.d("TESTname", name);
//                    Log.d("TESTname", addressEditText.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
