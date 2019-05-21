package com.ppl2jt.sawadikap_java;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ppl2jt.sawadikap_java.constant.Url;

import org.json.JSONArray;
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

public class EditProfileActivity extends AppCompatActivity {

    TextInputEditText fullnameEditText;
    TextInputEditText emailEditText;
    TextInputEditText usernameEditText;
    TextInputEditText oldPasswordEditText;
    TextInputEditText newPasswordEditText;
    TextInputEditText addressEditText;
    TextInputEditText phoneEditText;

    TextInputLayout fullnameInput;
    TextInputLayout emailInput;
    TextInputLayout usernameInput;
    TextInputLayout oldPasswordInput;
    TextInputLayout newPasswordInput;
    TextInputLayout addressInput;
    TextInputLayout phoneInput;

    SharedPreferences sharedPreferences;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", 0);

        fullnameEditText = findViewById(R.id.fullnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        oldPasswordEditText = findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        addressEditText = findViewById(R.id.addressEditText);
        phoneEditText = findViewById(R.id.phoneEditText);

        fullnameInput = findViewById(R.id.fullnameInput);
        emailInput = findViewById(R.id.emailInput);
        usernameInput = findViewById(R.id.usernameInput);
        oldPasswordInput = findViewById(R.id.oldPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        addressInput = findViewById(R.id.addressInput);
        phoneInput = findViewById(R.id.phoneInput);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Url.getUserCredentials(userId))
                .build();

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error",
                        "Failed to connect: " + e.getMessage());

                EditProfileActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditProfileActivity.this,
                                "Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {
                Log.i("Success", "Success: " + response.code());


                if (response.code() == 200) {

                    String stringResponse = response.body().string();

                    try {
                        final JSONArray jsonArray = new JSONArray(stringResponse);
                        final JSONObject jsonObject = jsonArray.getJSONObject(0);

                        Log.d("Credentials", stringResponse);

                        EditProfileActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditProfileActivity.this,
                                        "Success", Toast.LENGTH_SHORT)
                                        .show();
                                try {
                                    fullnameEditText.setText(jsonObject.getString("fullname"));
                                    emailEditText.setText(jsonObject.getString("email"));
                                    usernameEditText.setText(jsonObject.getString("username"));
//                        passwordEditText.setText(sharedPreferences.getString("password", ""));
                                    addressEditText.setText(jsonObject.getString("address"));
                                    phoneEditText.setText(jsonObject.getString("phone"));
                                } catch (Exception e) {
                                    Log.d("Exception", e.toString());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Log.d("Exceptionssss", e.toString());
                    }
                }
                countDownLatch.countDown();
            }
        });
    }

    public void finishEdit(View view) {
        new AlertDialog.Builder(EditProfileActivity.this)
                .setMessage("Apakah anda yakin?")
                .setCancelable(true)
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // Send Post Request
                                OkHttpClient client = new OkHttpClient();

                                RequestBody requestBody = new FormBody.Builder()
                                        .add("id_user", Integer.toString(userId))
                                        .add("fullname", fullnameEditText.getText().toString())
                                        .add("username", usernameEditText.getText().toString())
                                        .add("email", emailEditText.getText().toString())
//                                        .add("password", passwordEditText.getText().toString())
                                        .add("phone", phoneEditText.getText().toString())
                                        .add("address", addressEditText.getText().toString())
                                        .build();

                                Request request = new Request.Builder()
                                        .url(Url.UPDATE_USER)
                                        .post(requestBody)
                                        .build();

                                final CountDownLatch countDownLatch = new CountDownLatch(1);

                                client.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.i("Error",
                                                "Failed to connect: " + e.getMessage());

                                        EditProfileActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(EditProfileActivity.this,
                                                        "Failed",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        countDownLatch.countDown();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response)
                                            throws IOException {
                                        Log.i("Success", "Success: " + response.code());
                                        if (response.code() == 200) {
                                            EditProfileActivity.this.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(EditProfileActivity.this,
                                                            "Success", Toast.LENGTH_SHORT)
                                                            .show();
                                                }
                                            });

                                            String stringResponse = response.body().string();

                                            finish();

                                        }
                                        countDownLatch.countDown();
                                    }
                                });
                            }
                        })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
