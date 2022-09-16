package com.in.kistec.SignUpActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.in.kistec.LoginActivity;
import com.in.kistec.R;

public class SignUpOtpActivity extends AppCompatActivity {
    LinearLayout signupOtpLayout;
    EditText getOtp;
    String OtpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp);

        signupOtpLayout = findViewById(R.id.signupOtpLayout);
        getOtp = findViewById(R.id.getOtp);


        OtpData = getOtp.getText().toString();

//        private void sendVerificationCode(String number) {
//            progressDialog.show();
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    number,
//                    60,
//                    TimeUnit.SECONDS,
////                OTP_Verification.this,
//                    TaskExecutors.MAIN_THREAD,
//                    mCallBack
//            );
//            progressDialog.dismiss();
////        progressBar.setVisibility(View.GONE);
//        }


        signupOtpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}