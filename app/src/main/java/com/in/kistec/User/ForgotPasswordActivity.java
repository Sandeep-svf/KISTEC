package com.in.kistec.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.Check_Phone_Model;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SignupActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ImageView background , verification_otp_backgrounnd;
    EditText numOne, numTwo, numThree, numFour, numFive, numSix , forgot_password_number;
    ViewFlipper viewFlipper;
    LinearLayout forgot_password_submit;
    private  String mobileNumber ;
    TextView verify_layout;
    CountryCodePicker ccp;

    // Firebase
    public String phoneVerificationId;
    private FirebaseAuth mAuth;
    private String checkPhoneNumber= "",countryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        background = findViewById(R.id.background);
        verification_otp_backgrounnd = findViewById(R.id.verification_otp_backgrounnd);
        Glide.with(ForgotPasswordActivity.this).load(R.drawable.splash_background).into(background);
        Glide.with(ForgotPasswordActivity.this).load(R.drawable.splash_background).into(verification_otp_backgrounnd);


        viewFlipper = findViewById(R.id.view_flip);
        forgot_password_submit = findViewById(R.id.forgot_password_submit);
        forgot_password_number = findViewById(R.id.forgot_password_number);
        verify_layout = findViewById(R.id.verify_layout);
        ccp = findViewById(R.id.ccp);



        mAuth = FirebaseAuth.getInstance();
        verify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyCode(view);
            }
        });


        numOne = findViewById(R.id.numone);
        numTwo = findViewById(R.id.numtwo);
        numThree = findViewById(R.id.numthree);
        numFour = findViewById(R.id.numfour);
        numFive = findViewById(R.id.numfive);
        numSix = findViewById(R.id.numsix);


        numOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numOne.getText().toString().length() == 0) {
                    numTwo.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numTwo.getText().toString().length() == 0) {
                    numThree.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numTwo.getText().toString().length() == 0) {
                    numOne.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numThree.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numThree.getText().toString().length() == 0) {
                    numTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (numFour.getText().toString().length() == 0) {
                    numFive.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numFour.getText().toString().length() == 0) {
                    numThree.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        numFive.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("Runnig", "beforeTextChanged");
                if (numFive.getText().toString().length() == 0) {
                    numSix.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("Runnig", "onTextChanged");
                if (numFive.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Runnig", "afterTextChanged");

            }
        });

        numSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (numSix.getText().toString().length() == 0) {
                    numFive.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        forgot_password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobileNumber = forgot_password_number.getText().toString();
                String regax = "^[0-9]{10,13}$";

                if (forgot_password_number.equals("")) {
                    forgot_password_number.requestFocus();
                    forgot_password_number.setError(getResources().getString(R.string.enter_phone));
                } else if (forgot_password_number.getText().toString().length() < 9 || forgot_password_number.length() > 11 || mobileNumber.matches(regax) == false) {
                    forgot_password_number.setError(getResources().getString(R.string.right_number));
                } else {

                    String temp = mobileNumber.substring(0,1);

                    if(temp.equals("0"))
                    {
                        mobileNumber = mobileNumber.substring(1,mobileNumber.length());
                    }else
                    {
                        mobileNumber = mobileNumber;
                    }


                    Log.e("check",mobileNumber);

                    String fbUserPhoneNumber = "";

                   /* if (mobileNumber.length() == 9 || mobileNumber.length() == 10){
                        fbUserPhoneNumber = mobileNumber;
                        fbUserPhoneNumber ="+1" + mobileNumber;
                        sendVerificationCode(fbUserPhoneNumber);

                    }else{
                        Toast toast = Toast.makeText(ForgotPasswordActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }*/
                    check_phone_api();

                }
            }
        });

    }

    private void check_phone_api() {

        countryCode = ccp.getSelectedCountryCode();

        // show till load api data
        //checkPhoneNumber = "+1" + mobileNumber;
         checkPhoneNumber = "+"+countryCode+mobileNumber;
        Log.e("checkPhoneNumber", checkPhoneNumber);


        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Check_Phone_Model> call = API_Client.getClient().check_number(mobileNumber);

        call.enqueue(new Callback<Check_Phone_Model>() {
            @Override
            public void onResponse(Call<Check_Phone_Model> call, Response<Check_Phone_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            sendVerificationCode(checkPhoneNumber);
                            //Log.e("check",mobileNumber);
                          /*  Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                            intent.putExtra("mNumber", mobileNumber);
                            startActivity(intent);*/

                        } else {
                            Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getApplicationContext(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getApplicationContext(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getApplicationContext(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getApplicationContext(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getApplicationContext(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getApplicationContext(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getApplicationContext(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Check_Phone_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }



    public void verifyCode(View view) {
        String code = "" + numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        if (!code.equals("")) {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } else {
            Toast.makeText(this, "Enter the Correct varification Code", Toast.LENGTH_SHORT).show();
        }

    }
    private void sendVerificationCode(String mobileNumber) {

        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNumber, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                TaskExecutors.MAIN_THREAD, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.
        );

    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
           /* Intent intent=new Intent(getApplicationContext(),Verification_Activity.class);
            startActivity(intent);*/
            phoneVerificationId = s;
            viewFlipper.setDisplayedChild(1);

        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            signInWithPhoneAuthCredential(phoneAuthCredential);
            // checking if the code
            // is null or not.

            if (code != null) {
                //   Forget_Login();
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
//                edtOTP.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
//                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.

            Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Toast.makeText(ForgotPasswordActivity.this, "OTP Verification Successful", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                            intent.putExtra("mNumber", checkPhoneNumber);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }
}