package com.ppl2jt.sawadikap_java;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void finishEdit(View view) {
        new AlertDialog.Builder(EditProfileActivity.this)
                .setMessage("Apakah anda yakin?")
                .setCancelable(true)
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // Send Post Request

//                                Intent intent = new Intent(EditProfileActivity.this,
//                                        LoginActivity.class);
//                                finishAffinity();
//                                getSharedPreferences("PREFERENCE_STORY", MODE_PRIVATE)
//                                        .edit()
//                                        .putBoolean("isLoggedIn", false).apply();
//                                startActivity(intent);
                            }
                        })
                .setNegativeButton("Tidak", null)
                .show();
    }
}
