package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ppl2jt.sawadikap_java.constant.Url;
import com.ppl2jt.sawadikap_java.job.ViewDialog;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WardrobeDetailActivity extends AppCompatActivity {

    ImageView picture;
    TextView type;
    TextView age;
    TextView size;
    TextView condition;
    TextView status;
    ViewDialog viewDialog;
    int userId;
    int clothesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe_detail);

        viewDialog = new ViewDialog(this);

        userId = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
                .getInt("userId", 0);
        clothesId = getIntent().getIntExtra("id_pakaian", 0);

        picture = findViewById(R.id.fotoPakaian);
        type = findViewById(R.id.tipe);
        age = findViewById(R.id.usia);
        size = findViewById(R.id.ukuran);
        condition = findViewById(R.id.kondisi);
        status = findViewById(R.id.status);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("foto")).into(picture);
        type.setText(intent.getStringExtra("jenis_baju"));
        age.setText(intent.getStringExtra("jenis_usia"));
        size.setText(intent.getStringExtra("jenis_ukuran"));
        condition.setText(intent.getStringExtra("status"));
        status.setText("Belum Dibuat");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void backPressed(View view) {
        onBackPressed();
    }

    public void deleteFromWardrobe(View view) {
        // Call API Endpoint to delete the selected ID from wardrobe
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Url.deletePakaian(clothesId))
                .delete()
                .build();

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error", "Failed to connect: " + e.getMessage());

                WardrobeDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WardrobeDetailActivity.this, "Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Success", "Success: " + response.code());
                if (response.code() == 200) {
                    WardrobeDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WardrobeDetailActivity.this,
                                    "Success, deleted", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();

                }
                countDownLatch.countDown();
            }
        });
    }

    public void Sedekahkan(View view) {
        // Call API Endpoint to change the "sedekah" status
//        Log.d("SEDEKAH", "Clicked");

        viewDialog.showDialog();
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("id_user", Integer.toString(userId))
                .add("request_date", new Timestamp(System.currentTimeMillis()).toString())
                .add("status", "Diproses oleh kurir")
                .add("id_pakaian", Integer.toString(clothesId))
                .add("penerima", "")
                .build();

        Request request = new Request.Builder()
                .url(Url.SEDEKAH)
                .post(requestBody)
                .build();

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Error", "Failed to connect: " + e.getMessage());

                WardrobeDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WardrobeDetailActivity.this, "Failed",
                                Toast.LENGTH_SHORT).show();
//                        viewDialog.hideDialog();
                    }
                });
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("Success", "Success: " + response.code());
                if (response.code() == 201 || response.code() == 500) {
                    WardrobeDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WardrobeDetailActivity.this,
                                    "Success", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    finish();
                                }
                            }, 2000);
//                            viewDialog.hideDialog();
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });

    }
}
