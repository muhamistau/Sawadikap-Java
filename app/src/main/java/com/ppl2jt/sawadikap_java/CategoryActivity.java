package com.ppl2jt.sawadikap_java;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

public class CategoryActivity extends AppCompatActivity {

    ImageView baby, child, teen, adult;
    ImageView tShirt, shirt, dress, jacket;
    ImageView male, female;
    TextView s, m, l, xl;
    ImageView star1, star2, star3, star4, star5;
    TextView ageText, categoryText, genderText;

    String age, category, gender, size, rating;
    String imageUri, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        imageUri = getIntent().getStringExtra("image");
        imageUrl = getIntent().getStringExtra("imageUrl");

        baby = findViewById(R.id.age_baby);
        child = findViewById(R.id.age_child);
        teen = findViewById(R.id.age_teen);
        adult = findViewById(R.id.age_adult);
        ageText = findViewById(R.id.ageText);

        tShirt = findViewById(R.id.category_t_shirt);
        shirt = findViewById(R.id.category_shirt);
        dress = findViewById(R.id.category_dress);
        jacket = findViewById(R.id.category_jacket);
        categoryText = findViewById(R.id.categoryText);

        male = findViewById(R.id.gender_male);
        female = findViewById(R.id.gender_female);
        genderText = findViewById(R.id.genderText);

        s = findViewById(R.id.size_s);
        m = findViewById(R.id.size_m);
        l = findViewById(R.id.size_l);
        xl = findViewById(R.id.size_xl);

        star1 = findViewById(R.id.rating1);
        star2 = findViewById(R.id.rating2);
        star3 = findViewById(R.id.rating3);
        star4 = findViewById(R.id.rating4);
        star5 = findViewById(R.id.rating5);

        //* Age Category
        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgeImageColor(R.color.babyPink, R.color.unselected, R.color.unselected,
                        R.color.unselected, "Bayi");
                age = "Bayi";
            }
        });
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgeImageColor(R.color.unselected, R.color.childRed, R.color.unselected,
                        R.color.unselected, "Balita");
                age = "Balita";
            }
        });
        teen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgeImageColor(R.color.unselected, R.color.unselected, R.color.teenPurple,
                        R.color.unselected, "Remaja");
                age = "Remaja";
            }
        });
        adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgeImageColor(R.color.unselected, R.color.unselected, R.color.unselected,
                        R.color.adultBlue, "Dewasa");
                age = "Dewasa";
            }
        });

        //* Clothes Category
        tShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryImageColor(R.color.colorAccent, R.color.unselected, R.color.unselected,
                        R.color.unselected, "Kaos");
                category = "Kaos";
            }
        });
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryImageColor(R.color.unselected, R.color.colorAccent, R.color.unselected,
                        R.color.unselected, "Kemeja");
                category = "Kemeja";
            }
        });
        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryImageColor(R.color.unselected, R.color.unselected, R.color.colorAccent,
                        R.color.unselected, "Gaun");
                category = "Gaun";
            }
        });
        jacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategoryImageColor(R.color.unselected, R.color.unselected, R.color.unselected,
                        R.color.colorAccent, "Jaket");
                category = "Jaket";
            }
        });

        //* Gender Category
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGenderImageColor(R.color.adultBlue, R.color.unselected, "Laki-laki");
                gender = "Laki-laki";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGenderImageColor(R.color.unselected, R.color.babyPink, "Perempuan");
                gender = "Perempuan";
            }
        });

        //* Size Category
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewColor(R.color.colorAccent, R.color.unselected, R.color.unselected,
                        R.color.unselected);
                size = "S";
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewColor(R.color.unselected, R.color.colorAccent, R.color.unselected,
                        R.color.unselected);
                size = "M";
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewColor(R.color.unselected, R.color.unselected, R.color.colorAccent,
                        R.color.unselected);
                size = "L";
            }
        });
        xl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewColor(R.color.unselected, R.color.unselected, R.color.unselected,
                        R.color.colorAccent);
                size = "XL";
            }
        });

        //* Rating Category
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(R.drawable.ic_star_black_24dp, R.drawable.ic_star_border_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.drawable.ic_star_border_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.color.colorAccent,
                        R.color.unselected, R.color.unselected, R.color.unselected,
                        R.color.unselected);
                rating = "Tidak Layak";
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.drawable.ic_star_border_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.color.colorAccent,
                        R.color.colorAccent, R.color.unselected, R.color.unselected,
                        R.color.unselected);
                rating = "Agak Rusak";
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_black_24dp, R.drawable.ic_star_border_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.color.colorAccent,
                        R.color.colorAccent, R.color.colorAccent, R.color.unselected,
                        R.color.unselected);
                rating = "Layak Pakai";
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_border_black_24dp, R.color.colorAccent,
                        R.color.colorAccent, R.color.colorAccent, R.color.colorAccent,
                        R.color.unselected);
                rating = "Masih Bagus";
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_black_24dp, R.drawable.ic_star_black_24dp,
                        R.drawable.ic_star_black_24dp, R.color.colorAccent,
                        R.color.colorAccent, R.color.colorAccent, R.color.colorAccent,
                        R.color.colorAccent);
                rating = "Seperti Baru";
            }
        });
    }

    private void setRating(int drawable1, int drawable2, int drawable3, int drawable4,
                           int drawable5, int color1, int color2, int color3,
                           int color4, int color5) {
        star1.setImageDrawable(getDrawable(drawable1));
        star2.setImageDrawable(getDrawable(drawable2));
        star3.setImageDrawable(getDrawable(drawable3));
        star4.setImageDrawable(getDrawable(drawable4));
        star5.setImageDrawable(getDrawable(drawable5));
        ImageViewCompat.setImageTintList(star1,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color1)));
        ImageViewCompat.setImageTintList(star2,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color2)));
        ImageViewCompat.setImageTintList(star3,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color3)));
        ImageViewCompat.setImageTintList(star4,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color4)));
        ImageViewCompat.setImageTintList(star5,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color5)));

    }

    private void setTextViewColor(int color1, int color2, int color3, int color4) {
        s.setTextColor(ContextCompat.getColor(CategoryActivity.this, color1));
        m.setTextColor(ContextCompat.getColor(CategoryActivity.this, color2));
        l.setTextColor(ContextCompat.getColor(CategoryActivity.this, color3));
        xl.setTextColor(ContextCompat.getColor(CategoryActivity.this, color4));
    }

    private void setGenderImageColor(int color1, int color2, String gender) {
        ImageViewCompat.setImageTintList(male,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color1)));
        ImageViewCompat.setImageTintList(female,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color2)));
        genderText.setText(gender);
    }

    private void setCategoryImageColor(int color1, int color2, int color3, int color4,
                                       String category) {
        ImageViewCompat.setImageTintList(tShirt,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color1)));
        ImageViewCompat.setImageTintList(shirt,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color2)));
        ImageViewCompat.setImageTintList(dress,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color3)));
        ImageViewCompat.setImageTintList(jacket,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        color4)));
        categoryText.setText(category);
    }

    private void setAgeImageColor(int babyColor, int childColor, int teenColor, int adultColor,
                                  String age) {
        ImageViewCompat.setImageTintList(baby,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        babyColor)));
        ImageViewCompat.setImageTintList(child,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        childColor)));
        ImageViewCompat.setImageTintList(teen,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        teenColor)));
        ImageViewCompat.setImageTintList(adult,
                ColorStateList.valueOf(ContextCompat.getColor(CategoryActivity.this,
                        adultColor)));
        ageText.setText(age);
    }

    public void confirmationPageIntent(View view) {
        Intent intent = new Intent(CategoryActivity.this, ConfirmationActivity.class);
        intent.putExtra("age", age);
        intent.putExtra("category", category);
        intent.putExtra("gender", gender);
        intent.putExtra("size", size);
        intent.putExtra("rating", rating);
        intent.putExtra("image", imageUri);
//        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }
}
