package com.in.kistec.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.in.kistec.R;

public class AboutUsActivity extends AppCompatActivity {
ImageView about_us_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        about_us_background = findViewById(R.id.about_us_background);

        Glide.with(AboutUsActivity.this).load(R.drawable.splash_background).into(about_us_background);
    }
}