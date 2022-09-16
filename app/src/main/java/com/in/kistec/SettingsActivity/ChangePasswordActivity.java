package com.in.kistec.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;
import com.in.kistec.API_Model.Change_Password_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.LoginActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.User.splashActivity;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText confiemPassword, oldPassword, newPassword;
    String oldPasswordData, newPasswordData, confirmPasswordData,UserID, authorization;
    LinearLayout updatePasswordLayout;
    ImageView backArrow , old_password_eye, old_password_hidden ,
            new_password_eye , new_password_hidden , confirm_password_eye ,
            confirm_password_hidden , change_password_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confiemPassword = findViewById(R.id.confiemPassword);
        updatePasswordLayout = findViewById(R.id.updatePasswordLayout);
        backArrow = findViewById(R.id.backArrow);
        old_password_eye = findViewById(R.id.old_password_eye);
        old_password_hidden = findViewById(R.id.old_password_hidden);
        new_password_eye = findViewById(R.id.new_password_eye);
        new_password_hidden = findViewById(R.id.new_password_hidden);
        confirm_password_eye = findViewById(R.id.confirm_password_eye);
        confirm_password_hidden = findViewById(R.id.confirm_password_hidden);
        change_password_background = findViewById(R.id.change_password_background);

        Glide.with(ChangePasswordActivity.this).load(R.drawable.splash_background).into(change_password_background);


        confirm_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_password_eye.setVisibility(View.GONE);
                confirm_password_hidden.setVisibility(View.VISIBLE);
                confiemPassword.setTransformationMethod(null);

            }
        });

        confirm_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_password_eye.setVisibility(View.VISIBLE);
                confirm_password_hidden.setVisibility(View.GONE);
                confiemPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });


        new_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password_eye.setVisibility(View.GONE);
                new_password_hidden.setVisibility(View.VISIBLE);
                newPassword.setTransformationMethod(null);

            }
        });

        new_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password_eye.setVisibility(View.VISIBLE);
                new_password_hidden.setVisibility(View.GONE);
                newPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });

        old_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_password_eye.setVisibility(View.GONE);
                old_password_hidden.setVisibility(View.VISIBLE);
                oldPassword.setTransformationMethod(null);

            }
        });

        old_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_password_eye.setVisibility(View.VISIBLE);
                old_password_hidden.setVisibility(View.GONE);
                oldPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });





        // Validation for confirm password


        //geting userID data
        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();

            }
        });

        updatePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validation  firld can not be empty

                if (oldPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    oldPassword.setError("This field can not be blank");
                    oldPassword.requestFocus();
                    return;
                }

                if (newPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    newPassword.setError("This field can not be blank");
                    newPassword.requestFocus();
                    return;
                }

                if (confiemPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    confiemPassword.setError("This field can not be blank");
                    confiemPassword.requestFocus();
                    return;
                }


                // Password did not match validation
                if(!newPassword.getText().toString().equals(confiemPassword.getText().toString()) ) {
                    confiemPassword.setError("Password did not match");
                    confiemPassword.requestFocus();
                    return;
                }




                oldPasswordData = oldPassword.getText().toString();
                newPasswordData = newPassword.getText().toString();
                confirmPasswordData = confiemPassword.getText().toString();


                // API
                update_password_api();
            }
        });

    }

    private void update_password_api() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Change_Password_Model> call = API_Client.getClient().changePassword(authorization , UserID,oldPasswordData,newPasswordData);

        call.enqueue(new Callback<Change_Password_Model>() {
            @Override
            public void onResponse(Call<Change_Password_Model> call, Response<Change_Password_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True")) {

                            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getApplicationContext(),"Password Change Successfully",Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
//                            startActivity(intent);
//                            finish();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Change_Password_Model> call, Throwable t) {
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