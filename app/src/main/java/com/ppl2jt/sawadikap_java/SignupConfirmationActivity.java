package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ppl2jt.sawadikap_java.constant.Url;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupConfirmationActivity extends AppCompatActivity {

    TextView emailTextView;
    TextView nameTextView;
    TextView usernameTextView;
    TextView addressTextView;
    String email;
    String name;
    String username;
    String address;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_confirmation);

        emailTextView = findViewById(R.id.emailConfirmation);
        nameTextView = findViewById(R.id.nameConfirmation);
        usernameTextView = findViewById(R.id.usernameConfirmation);
        addressTextView = findViewById(R.id.addressConfirmation);
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        address = getIntent().getStringExtra("address");
        password = getIntent().getStringExtra("password");
//        Log.d("email", email);

        emailTextView.setText(email);
        nameTextView.setText(name);
        usernameTextView.setText(username);
        addressTextView.setText(address);
    }

    public void signup(View view) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("email", getIntent().getStringExtra("email"))
                .add("fullname", getIntent().getStringExtra("name"))
                .add("username", getIntent().getStringExtra("username"))
                .add("address", getIntent().getStringExtra("email"))
                .add("password", getIntent().getStringExtra("password"))
                .build();

        Request request = new Request.Builder()
                .url(Url.SIGNUP)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error", "Failed to connect: " + e.getMessage());

                SignupConfirmationActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignupConfirmationActivity.this, "Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Success", "Success: " + response.code());
                if (response.code() == 201) {
                    SignupConfirmationActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupConfirmationActivity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    String stringResponse = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(stringResponse);
                        final String email = jsonObject.getString("email");
                        final String username = jsonObject.getString("username");
//                        final String picUrl = jsonObject.getString("url_foto");
                        final int userId = jsonObject.getInt("id");

                        Intent intent = new Intent(SignupConfirmationActivity.this,
                                MainActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("username", username);
//                        intent.putExtra("picUrl", picUrl);
                        intent.putExtra("userId", userId);

                        getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE).edit()
                                .putString("email", email)
                                .putString("username", username)
//                                .putString("picUrl", picUrl)
                                .putInt("userId", userId)
                                .putBoolean("isLoggedIn", true).apply();

                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (response.code() == 500) {
                    usernameTextView.setTextColor(getResources().getColor(R.color.materialRed));
                } else if (response.code() == 422) {
                    emailTextView.setTextColor(getResources().getColor(R.color.materialRed));
                }
            }
        });
    }
}
