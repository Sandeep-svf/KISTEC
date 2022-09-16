package com.in.kistec.SignUpActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.in.kistec.R;
import com.in.kistec.User.UserHomeActivity;

public class SignUpAddProfileActivity extends AppCompatActivity {
    LinearLayout signupAddProfileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_add_profile);

        signupAddProfileLayout = findViewById(R.id.signupAddProfileLayout);


        signupAddProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}