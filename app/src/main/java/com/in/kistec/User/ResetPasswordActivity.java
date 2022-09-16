package com.in.kistec.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.Check_Phone_Model;
import com.in.kistec.API_Model.Reset_Model;
import com.in.kistec.LoginActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    ImageView background_reset_password , signin_password_eye , signin_password_hidden , sign_in_confirm_password_eye , sign_in_confirm_password_hidden;
    EditText userPassword_reset , userConfirmPassword_reset;
    LinearLayout submit_layout_reset;
    private  String password , confirmPassword ;
    private  String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mobileNumber = getIntent().getStringExtra("mNumber");


        background_reset_password = findViewById(R.id.background_reset_password);
        sign_in_confirm_password_hidden = findViewById(R.id.sign_in_confirm_password_hidden);
        sign_in_confirm_password_eye = findViewById(R.id.sign_in_confirm_password_eye);
        signin_password_eye = findViewById(R.id.signin_password_eye);
        signin_password_hidden = findViewById(R.id.signin_password_hidden);
        userPassword_reset = findViewById(R.id.userPassword_reset);
        submit_layout_reset = findViewById(R.id.submit_layout_reset);
        userConfirmPassword_reset = findViewById(R.id.userConfirmPassword_reset);
        Glide.with(ResetPasswordActivity.this).load(R.drawable.splash_background).into(background_reset_password);

        sign_in_confirm_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_confirm_password_eye.setVisibility(View.GONE);
                sign_in_confirm_password_hidden.setVisibility(View.VISIBLE);
                userConfirmPassword_reset.setTransformationMethod(null);
                userConfirmPassword_reset.setSelection(userConfirmPassword_reset.getText().length());
            }
        });

        sign_in_confirm_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in_confirm_password_hidden.setVisibility(View.GONE);
                sign_in_confirm_password_eye.setVisibility(View.VISIBLE);
                userConfirmPassword_reset.setTransformationMethod(new PasswordTransformationMethod());
                userConfirmPassword_reset.setSelection(userConfirmPassword_reset.getText().length());
            }
        });

        signin_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signin_password_eye.setVisibility(View.GONE);
                signin_password_hidden.setVisibility(View.VISIBLE);
                userPassword_reset.setTransformationMethod(null);
                userPassword_reset.setSelection(userPassword_reset.getText().length());
            }
        });

        signin_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin_password_hidden.setVisibility(View.GONE);
                signin_password_eye.setVisibility(View.VISIBLE);
                userPassword_reset.setTransformationMethod(new PasswordTransformationMethod());
                userPassword_reset.setSelection(userPassword_reset.getText().length());
            }
        });

        submit_layout_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                password = userPassword_reset.getText().toString();
                confirmPassword = userConfirmPassword_reset.getText().toString();

                if(userPassword_reset.getText().toString().equals(""))
                {
                        userPassword_reset.setError("This field can not be blank.");
                    userPassword_reset.requestFocus();
                    return;
                }else  if (userConfirmPassword_reset.getText().toString().equals(""))
                {
                    userConfirmPassword_reset.setError("This field can not be blank.");
                    userConfirmPassword_reset.requestFocus();
                    return;
                }else if(! userPassword_reset.getText().toString().equals( userConfirmPassword_reset.getText().toString()))
                {
                    userConfirmPassword_reset.setError("Password did not match");
                    userConfirmPassword_reset.requestFocus();
                    return;
                }else
                {
                    reset_password_api();
                }
            }
        });
    }

    private void reset_password_api() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Reset_Model> call = API_Client.getClient().reset_password(mobileNumber,password);

        call.enqueue(new Callback<Reset_Model>() {
            @Override
            public void onResponse(Call<Reset_Model> call, Response<Reset_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

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
            public void onFailure(Call<Reset_Model> call, Throwable t) {
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
}