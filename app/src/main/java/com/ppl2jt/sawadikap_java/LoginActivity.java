package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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
    }

    public void login(View view) {

        Log.d("logging0", "pressed");

        OkHttpClient client = new OkHttpClient();
        String url = "http://sawadikap-endpoint.herokuapp.com/api/login";

        RequestBody requestBody = new FormBody.Builder()
                .add("email", emailInput.getText().toString())
                .add("password", passwordInput.getText().toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
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
                        Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });

                    String stringResponse = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(stringResponse);
                        final String email = jsonObject.getString("email");
                        final String username = jsonObject.getString("username");
                        final String picUrl = jsonObject.getString("url_foto");

                        Log.d("logging1", email);
                        Log.d("logging2", username);
                        Log.d("logging3", picUrl);

                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
                        intent.putExtra("picUrl", picUrl);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
