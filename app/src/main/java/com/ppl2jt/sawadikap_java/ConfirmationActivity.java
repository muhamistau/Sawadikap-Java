package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ppl2jt.sawadikap_java.job.UploadImage;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmationActivity extends AppCompatActivity {

    ImageView picture;
    TextView age, category, gender, size, rating;
    String imageUrl, ageValue, categoryValue, genderValue, sizeValue, ratingValue;
    Intent intent;
    int userId;

    Uri downloadUri;

    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        userId = getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
                .getInt("userId", 0);

        storageRef = FirebaseStorage.getInstance().getReference("user-image");
        databaseRef = FirebaseDatabase.getInstance().getReference("user-image");

        picture = findViewById(R.id.imageView);
        age = findViewById(R.id.age);
        category = findViewById(R.id.category);
        gender = findViewById(R.id.gender);
        size = findViewById(R.id.size);
        rating = findViewById(R.id.rating);

        intent = getIntent();

//        imageUrl = intent.getStringExtra("imageUrl");
        ageValue = intent.getStringExtra("age");
        categoryValue = intent.getStringExtra("category");
        genderValue = intent.getStringExtra("gender");
        sizeValue = intent.getStringExtra("size");
        ratingValue = intent.getStringExtra("rating");

        picture.setImageURI(Uri.parse(intent.getStringExtra("image")));
        age.setText("Usia: " + ageValue);
        category.setText("Jenis Pakaian: " + categoryValue);
        gender.setText("Jenis Kelamin: " + genderValue);
        size.setText("Ukuran: " + sizeValue);
        rating.setText("Kondisi: " + ratingValue);
    }

    public void done(View view) {

//            progressBar.setIndeterminate(true);

        Uri imageUri = Uri.parse(intent.getStringExtra("image"));

        if (imageUri != null) {
            storageRef.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                    Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();

                        UploadImage upload = new UploadImage("Image",
                                downloadUri.toString());

                        imageUrl = downloadUri.toString();

                        databaseRef.push().setValue(upload);

                        Toast.makeText(ConfirmationActivity.this, "Gambar berhasil diunggah",
                                Toast.LENGTH_SHORT).show();

                        OkHttpClient client = new OkHttpClient();
                        String url = "http://sawadikap-endpoint.herokuapp.com/api/input";

                        RequestBody requestBody = new FormBody.Builder()
                                .add("id_user", Integer.toString(userId))
                                .add("foto", imageUrl) // Belum ada imageUrl
                                .add("jenis_usia", ageValue)
                                .add("jenis_ukuran", sizeValue)
                                .add("jenis_gender", genderValue)
                                .add("jenis_baju", categoryValue)
                                .add("status", ratingValue)
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

                                ConfirmationActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ConfirmationActivity.this, "Failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                                countDownLatch.countDown();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Log.i("Success", "Success: " + response.code());
                                if (response.code() == 201) {
                                    ConfirmationActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ConfirmationActivity.this,
                                                    "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    String stringResponse = response.body().string();

                                    try {
                                        Intent intent = new Intent(ConfirmationActivity.this,
                                                MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
                    } else {
                        Toast.makeText(ConfirmationActivity.this, "gagal mengunggah: " +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Gambar belum diambil", Toast.LENGTH_SHORT).show();
        }
    }
}
