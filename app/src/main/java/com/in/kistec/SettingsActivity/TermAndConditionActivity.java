package com.in.kistec.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.in.kistec.R;

public class TermAndConditionActivity extends AppCompatActivity {
    ImageView back_arrow_tmc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition);

        WebView web = (WebView) findViewById(R.id.myweb1);
         back_arrow_tmc = findViewById(R.id.back_arrow_tmc);

        back_arrow_tmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        web.setWebViewClient(new WebViewClient());

        web.getSettings().setLoadsImagesAutomatically(true);

        web.getSettings().setJavaScriptEnabled(true);

        web.getSettings().setBuiltInZoomControls(true);

        web.getSettings().setSupportZoom(true);

        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setAllowContentAccess(true);

        web.loadUrl("https://kistec.org/terms-conditions");
        web.setWebViewClient(new WebViewClient());

    }
}