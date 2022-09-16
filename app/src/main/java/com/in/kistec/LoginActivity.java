package com.in.kistec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.Retrofit.Api;
import com.in.kistec.Retrofit.ApiClient;
import com.in.kistec.User.ForgotPasswordActivity;
import com.in.kistec.User.UserHomeActivity;
import com.in.kistec.User.splashActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.text.CollationElementIterator;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//*********  key alias - crb ,  key password - crb123   **************//
public class LoginActivity extends AppCompatActivity {
    LinearLayout loginLayout, adminChooseLayout, userChooseLayout;
    TextView signupLayout, forgotPasswordLayout;
    EditText getUserPassword, getUserEmail;
    String userEmail, userPassword;
    private int UserID;
    private String authorization;
    private String device_token;
    ImageView hiden_password_image, visibale_password_image, login_background, login_logo;
    private CollationElementIterator edit;

    // Parent ->1
    // Student -> 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginLayout = findViewById(R.id.loginLayout);
        signupLayout = findViewById(R.id.signupLayout);
        getUserPassword = findViewById(R.id.getUserPassword);
        getUserEmail = findViewById(R.id.getUserEmail);
        hiden_password_image = findViewById(R.id.hiden_password_image);
        visibale_password_image = findViewById(R.id.visibale_password_image);
        login_background = findViewById(R.id.login_background);
        login_logo = findViewById(R.id.login_logo);

        Glide.with(LoginActivity.this).load(R.drawable.logo_final).into(login_logo);
        Glide.with(LoginActivity.this).load(R.drawable.splash_background).into(login_background);

        hiden_password_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibale_password_image.setVisibility(View.VISIBLE);
                hiden_password_image.setVisibility(View.GONE);
                getUserPassword.setTransformationMethod(null);
                getUserPassword.setSelection(getUserPassword.getText().length());

            }
        });
        visibale_password_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiden_password_image.setVisibility(View.VISIBLE);
                visibale_password_image.setVisibility(View.GONE);

                getUserPassword.setTransformationMethod(new PasswordTransformationMethod());
                getUserPassword.setSelection(getUserPassword.getText().length());
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ritik", "getInstanceId failed", task.getException());
                            return;
                        }

                        //Get new Instance ID token
                        device_token = task.getResult().getToken();
                        Log.e("tokenn", "" + device_token);
                        Log.e("tokkkk", device_token.length() + "");

//                        Toast.makeText(getApplicationContext(),device_token,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, device_token, Toast.LENGTH_SHORT).show();
//                        edit.setText(device_token);
                    }
                });

        forgotPasswordLayout = findViewById(R.id.forgotPasswordLayout);

        forgotPasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String uri = String.valueOf(Uri.parse("https://itdevelopmentservices.com/creditRating/forget_pass"));
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(browserIntent);

                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        signupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling sign up activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                // finish();
            }
        });

        loginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get userName and Password from user
                userEmail = getUserEmail.getText().toString();
                userPassword = getUserPassword.getText().toString();

                Log.e("tokkkk", userEmail);
                Log.e("tokkkk", userPassword);

                if (userEmail.equals("")) {
                    getUserEmail.setError("This field can not be blank");
                    getUserEmail.requestFocus();
                    return;
                }

                String email = getUserEmail.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                // onClick of button perform this simplest code.
                if (email.matches(emailPattern)) {
                    //  Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    getUserEmail.setError("Please enter valid email Address");
                    getUserEmail.requestFocus();
                    return;
                }

                if (userPassword.equals("")) {
                    getUserPassword.setError("This field can not be blank");
                    getUserPassword.requestFocus();
                    return;
                } else {
                    login_api();
                }
            }
        });
    }

    private void login_api() {

        // show till load api data

        Log.e("user_id", "  " + device_token + " " + userEmail + " " + userPassword);

        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Api service = ApiClient.getClient().create(Api.class);
        retrofit2.Call<Login_Model> call = service.login(device_token, userEmail, userPassword);

       // retrofit2.Call<Login_Model> call = API_Client.getClient().login(device_token, userEmail, userPassword);

        call.enqueue(new Callback<Login_Model>() {
            @Override
            public void onResponse(retrofit2.Call<Login_Model> call, retrofit2.Response<Login_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                       /* String message = response.body().getMessage();
                        String success = response.body().getSuccess();*/

                        Login_Model login_models = response.body();
                        String success = login_models.getSuccess();
                        String message = login_models.getMessage();

                        Log.e("user_id", "    Falses  " + success+"  1  "+new Gson().toJson(response.body())+"  2 "+message);

                        if (success.equals("true") || success.equals("True")) {

                            Login_Result login_result = login_models.getData();
                            UserID = login_result.getId();
                            authorization = login_result.getAuthToken();

                            Log.e("user_id", String.valueOf(UserID));
                            Log.e("user_id", String.valueOf(authorization));
                            Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();

                            //Using share preferance for setting userID data

                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", String.valueOf(UserID));
                            editor.putString("authorization", String.valueOf(authorization));
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                            pd.dismiss();

                            Log.e("user_id", "    False");
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Log.e("user_id", "    Message");
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

                            Log.e("user_id", "    Exception");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    Log.e("user_id", "    Exception  "+e.getMessage()+"  "+e.toString());
                }
            }

            @Override
            public void onFailure(Call<Login_Model> call, Throwable t) {
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