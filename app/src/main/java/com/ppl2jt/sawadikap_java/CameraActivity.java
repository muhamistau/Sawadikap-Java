package com.ppl2jt.sawadikap_java;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class CameraActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    ImageView cameraImage;
    Button takePicture;
    ProgressBar progressBar;

    Uri imageUri;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraImage = findViewById(R.id.cameraImage);
        takePicture = findViewById(R.id.takePicture);
        progressBar = findViewById(R.id.progress_bar);
        storageRef = FirebaseStorage.getInstance().getReference("user-image");
        databaseRef = FirebaseDatabase.getInstance().getReference("user-image");
    }

    public void takePicture(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        imageUri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            cameraImage.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadPic(View view) {
//        if (imageUri != null) {
//            final StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." +
//                    getFileExtension(imageUri));
//
//            fileRef.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            }, 500);
//
//                            Toast.makeText(CameraActivity.this, "berhasil diunggah",
//                                    Toast.LENGTH_SHORT).show();
//
//                            String uploadId = databaseRef.push().getKey();
//
//                            UploadImage uploadImage = new UploadImage("Image",
//                                    storageRef.child("user-image/" + uploadId).getDownloadUrl()
//                                            .toString());
//
//                            databaseRef.child(uploadId).setValue(uploadImage);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(CameraActivity.this, e.getMessage(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() /
//                                    taskSnapshot.getTotalByteCount());
//                            progressBar.setProgress((int) progress);
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "Gambar belum diambil", Toast.LENGTH_SHORT).show();
//        }

        progressBar.setIndeterminate(true);

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
                        Uri downloadUri = task.getResult();

                        UploadImage upload = new UploadImage("Image",
                                downloadUri.toString());

                        databaseRef.push().setValue(upload);

                        Toast.makeText(CameraActivity.this, "Gambar berhasil diunggah",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CameraActivity.this, "upload failed: " +
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setIndeterminate(false);
                }
            });
        } else {
            Toast.makeText(this, "Gambar belum diambil", Toast.LENGTH_SHORT).show();
            progressBar.setIndeterminate(false);
        }
    }
}
