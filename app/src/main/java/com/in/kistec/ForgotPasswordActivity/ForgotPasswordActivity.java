package com.in.kistec.ForgotPasswordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.in.kistec.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    LinearLayout forgotPhoneEmailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPhoneEmailLayout = findViewById(R.id.forgotPhoneEmailLayout);

        forgotPhoneEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivityTwo.class);
                startActivity(intent);
            }
        });
    }
}