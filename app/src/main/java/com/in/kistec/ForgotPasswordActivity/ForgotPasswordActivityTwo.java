package com.in.kistec.ForgotPasswordActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.in.kistec.LoginActivity;
import com.in.kistec.R;

public class ForgotPasswordActivityTwo extends AppCompatActivity {
    LinearLayout forgotPhoneEmailLayoutTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_two);
        forgotPhoneEmailLayoutTwo = findViewById(R.id.forgotPhoneEmailLayoutTwo);

        forgotPhoneEmailLayoutTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}