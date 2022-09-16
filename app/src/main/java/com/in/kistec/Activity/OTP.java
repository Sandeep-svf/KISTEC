package com.in.kistec.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.in.kistec.R;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    ProgressDialog progressDialog;
    String verificationId;
    //    private FirebaseAuth mAuth;
    Button btn_send;


    private static PhoneAuthProvider.ForceResendingToken mResendToken;
    private static FirebaseAuth mAuth;
    private static String phoneNum;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btn_send = findViewById(R.id.btn_send);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        mAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            getWindow().getDecorView().setImportantForAutofill(
                    View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
        }
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendVerificationCode("+919625326569");

                try {
                    verifyPhone("+919625326569",mCallBacks);

                }catch (Exception e)
                {
                    Toast.makeText(OTP.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationId = s;
//        }
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
////                editText.setText(code);
////                verifyCode(code);
//                Toast.makeText(OTP.this, ""+code, Toast.LENGTH_SHORT).show();
//                if(!(code.equals("")))
//                {
//                    verifyCode(code);
//                    char a = code.charAt(0);
//                    char b = code.charAt(1);
//                    char c = code.charAt(2);
//                    char d = code.charAt(3);
//                    char e = code.charAt(4);
//                    char f = code.charAt(5);
//
////                    otp_textbox_one.setText(String.valueOf(a));
////                    otp_textbox_two.setText(String.valueOf(b));
////                    otp_textbox_three.setText(String.valueOf(c));
////                    otp_textbox_four.setText(String.valueOf(d));
////                    otp_textbox_five.setText(String.valueOf(e));
////                    otp_textbox_six.setText(String.valueOf(f));
//
//                }
//
//
//
////                Toast.makeText(SecondStep.this, "OtpCode "+code, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
////            Not_found_country_mobile_data();
//            Toast.makeText(OTP.this, "erorrr: "+e.getMessage(), Toast.LENGTH_LONG).show();
////            progressBar.setVisibility(View.GONE);
//            progressDialog.dismiss();
//        }
//    };
//    private void sendVerificationCode(String number) {
//        progressDialog.show();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60,
//                TimeUnit.SECONDS,
////                OTP_Verification.this,
//                TaskExecutors.MAIN_THREAD,
//                mCallBack
//        );
////        progressDialog.dismiss();
////        progressBar.setVisibility(View.GONE);
//    }
//
//
//
//    private void verifyCode(String code) {
//        try {
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
////        progressBar.setVisibility(View.VISIBLE);
//            progressDialog.show();
//            signInWithCredential(credential);
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    private void signInWithCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
////                            Login(MobileNo.replace("+91",""),Zipcode,sharedPreference_main.get_token());
//
//                            progressDialog.dismiss();
//                            Toast.makeText(OTP.this, "Ok", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(getApplicationContext(), "Ok OTP !", Toast.LENGTH_SHORT).show();
////                            startActivity(new Intent(this,Forget_Pass.class));
////                            sharedPreference_main.set_Mobile_no(MobileNo.replace("+91",""));
////                            sharedPreference_main.set_Mobile_No(MobileNo.replace("+1",""));
//
////                            finish();
//
////                            if(Type.equals("D"))
////                            {
////                                Login(MobileNo,sharedPreference_main.get_Select_zipcode(),sharedPreference_main.get_token());
////
//////                                progressBar.setVisibility(View.VISIBLE);
////                                progressDialog.dismiss();
////                            }
////                            else {
////                                Login(MobileNo,sharedPreference_main.get_Select_zipcode(),sharedPreference_main.get_token());
////
////                                progressDialog.dismiss();
//////                                progressBar.setVisibility(View.VISIBLE);
////                            }
//
//
////                            Intent intent = new Intent(SecondStep.this, ProfileActivity.class);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////
////                            startActivity(intent);
//
//                        } else {
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Wrong OTP !", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(SecondStep.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//}


    final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.d("JEJE", "onVerificationCompleted:" + phoneAuthCredential);
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            try {
                Log.w("JEJE", "onVerificationFailed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.d("JEJE", "INVALID REQUEST");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.d("JEJE", "Too many Request");
                }
            }catch (Exception es)
            {
                Toast.makeText(OTP.this, ""+e, Toast.LENGTH_SHORT).show();
            }

        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {


            super.onCodeSent(s, forceResendingToken);

            Log.d("JEJE", "onCodeSent:" + s);
            mResendToken = forceResendingToken;
//            phoneNum = editText.getText().toString();
            phoneNum = "+917905335400";
//            loadVerification(s, phoneNum);
        }
    };
//        submit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            phoneNum = editText.getText().toString();
//            verifyPhone(phoneNum,mCallBacks);
//        }
//
//    });



    public void loadVerification(String codeID, String phone){
//        Verification verification = new Verification();
//        Bundle args = new Bundle();
//        args.putString(Verification.ARGS_PHONE, phone);
//        args.putString(Verification.ARGS_VER_CODE, codeID);
//        verification.setArguments(args);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment, verification).commit();
    }


    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        showProgressDialog();
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(OTP.this, "Ok Code", Toast.LENGTH_SHORT).show();
//                    FirebaseUser user = task.getResult().getUser();
//                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    FragmentManager fragmentManager =getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.fragment, new Success()).commit();
                }else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(OTP.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                    }
                }
                hideProgressDialog();
            }
        });
    }

    public void verifyPhone(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallback
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}