package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ppl2jt.sawadikap_java.constant.Url;
import com.ppl2jt.sawadikap_java.job.Checker;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    Button signupButton;
    TextInputEditText emailInput;
    TextInputEditText passwordInput;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupButton = findViewById(R.id.buttonDaftar);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginText = findViewById(R.id.text_login);
    }

    public void login(View view) {
        //* For Development purpose
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();

        boolean flagEmail = false;
        boolean flagPass = false;

        Log.d("logging0", "pressed");

        if (emailInput.getText().toString().equals("")) {
            emailLayout.setError("Kolom Email tidak boleh kosong");
        } else if (!Checker.isEmailValid(emailInput.getText().toString())) {
            emailLayout.setError("Alamat email tidak valid");
        } else {
            flagEmail = true;
            emailLayout.setErrorEnabled(false);
        }

        if (passwordInput.getText().toString().equals("")) {
            passwordLayout.setError("Kolom Kata sandi tidak boleh kosong");
        } else {
            flagPass = true;
            passwordLayout.setErrorEnabled(false);
        }

        if (flagEmail && flagPass) {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("email", emailInput.getText().toString())
                    .add("password", passwordInput.getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url(Url.LOGIN)
                    .post(requestBody)
                    .build();

            final CountDownLatch countDownLatch = new CountDownLatch(1);

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("Error", "Failed to connect: " + e.getMessage());

                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Tidak ada koneksi internet",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                    countDownLatch.countDown();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i("Success", "Success: " + response.code());
                    if (response.code() == 200) {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(LoginActivity.this, "Success",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        String stringResponse = response.body().string();

                        try {
                            JSONObject jsonObject = new JSONObject(stringResponse);
                            final String email = jsonObject.getString("email");
                            final String username = jsonObject.getString("username");
                            final String picUrl = jsonObject.getString("url_foto");
                            final int userId = jsonObject.getInt("id");

//                        Log.d("logging1", email);
//                        Log.d("logging2", username);
//                        Log.d("logging3", picUrl);

                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("username", username);
                            intent.putExtra("picUrl", picUrl);
                            intent.putExtra("userId", userId);

                            getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE).edit()
                                    .putString("email", email)
                                    .putString("username", username)
                                    .putString("picUrl", picUrl)
                                    .putInt("userId", userId)
                                    .putBoolean("isLoggedIn", true).apply();

                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,
                                        "Email atau Kata sandi anda salah",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    countDownLatch.countDown();
                }
            });

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
