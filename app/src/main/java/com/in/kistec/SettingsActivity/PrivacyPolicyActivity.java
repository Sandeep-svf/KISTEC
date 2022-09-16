package com.in.kistec.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.in.kistec.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    ImageView back_arrow_pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        back_arrow_pp = findViewById(R.id.back_arrow_pp);
        back_arrow_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebView web = (WebView) findViewById(R.id.myweb);

        web.setWebViewClient(new WebViewClient());

        web.getSettings().setLoadsImagesAutomatically(true);

        web.getSettings().setJavaScriptEnabled(true);

        web.getSettings().setBuiltInZoomControls(true);

        web.getSettings().setSupportZoom(true);

        web.getSettings().setLoadWithOverviewMode(true);

        web.getSettings().setUseWideViewPort(true);

        web.getSettings().setAllowContentAccess(true);
        https://itdevelopmentservices.com/creditRating/terms-conditions
        web.loadUrl("https://kistec.org/Privacy-policy");
        web.setWebViewClient(new WebViewClient());

    }
}