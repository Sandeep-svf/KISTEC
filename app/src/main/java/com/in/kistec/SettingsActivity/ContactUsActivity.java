package com.in.kistec.SettingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.ContactUsResult;

import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.API_Result_Data.Record;
import com.in.kistec.API_Model.ContactUsModel;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.notification_Count_Model;

import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    ImageView contact_us_background , contact_us_logo, backArrow;
    TextView phonenumber, address, useremail;
    LinearLayout location, phone_numbar, email_address, submit_btn;
    String UserID, authorization;
    String UserAddress, Stremail, PhoneNumber;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        contact_us_background = findViewById(R.id.contact_us_background);
        contact_us_logo = findViewById(R.id.contact_us_logo);
        location = findViewById(R.id.location);
        phone_numbar = findViewById(R.id.phone_numbar);
        email_address = findViewById(R.id.email_address);
        backArrow = findViewById(R.id.backArrow);
        useremail = findViewById(R.id.useremail);
        address = findViewById(R.id.address);
        phonenumber = findViewById(R.id.phonenumber);

        Glide.with(ContactUsActivity.this).load(R.drawable.splash_background).into(contact_us_background);
        Glide.with(ContactUsActivity.this).load(R.drawable.logo_final).into(contact_us_logo);

        SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        Log.e("authorization", authorization+"  "+UserID);

        backArrow.setOnClickListener(v -> finish());

//        submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check_validation())
//                    PhoneNumber = phonenumber.getText().toString();
//                    UserAddress = address.getText().toString();
//                    Stremail = useremail.getText().toString();
//
//                ContactUs_Api();
//            }
//        });

      ContactUs_Api();

    }

    private void ContactUs_Api() {

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<ContactUsModel> call = API_Client.getClient().Contaact_Us(UserAddress, Stremail,PhoneNumber);

        call.enqueue(new Callback<ContactUsModel>() {
            @Override
            public void onResponse(Call<ContactUsModel> call, Response<ContactUsModel> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            ContactUsModel contactUsModel = response.body();
                            ContactUsResult  contactUsResult = contactUsModel.getRecord2();

                            String temp = String.valueOf(contactUsResult.getId());
                            String addrress = String.valueOf(contactUsResult.getAddress());
                            String mail = String.valueOf(contactUsResult.getMail());
                            String phone = String.valueOf(contactUsResult.getPhone());

                          /*  useremail.setText(contactUsResult.getMail());
                            phonenumber.setText(contactUsResult.getPhone());*/

                            Log.e("addrress",addrress);

                            if(addrress.equalsIgnoreCase("") || addrress.equalsIgnoreCase("null"))
                            {
                                // HIDE
                                location.setVisibility(View.GONE);

                            }else
                            {
                                // Set value
                                location.setVisibility(View.VISIBLE);
                                address.setText(addrress);

                            }

                            if(mail.equalsIgnoreCase("") || mail.equalsIgnoreCase("null") )
                            {
                                // HIDE
                                email_address.setVisibility(View.GONE);

                            }else
                            {
                                // Set value
                                email_address.setVisibility(View.VISIBLE);
                                useremail.setText(mail);

                            }

                            if(phone.equalsIgnoreCase("") || phone.equalsIgnoreCase("null"))
                            {
                                // HIDE
                                phone_numbar.setVisibility(View.GONE);

                            }else
                            {
                                // Set value
                                phone_numbar.setVisibility(View.VISIBLE);
                                phonenumber.setText(phone);

                            }
                        }
                        else {

                            //Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();

                            pd.dismiss();
                        }

                    } else {
                        try     {
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
            public void onFailure(Call<ContactUsModel> call, Throwable t) {
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

    private boolean check_validation() {
        Stremail = useremail.getText().toString();

        if(address.getText().toString().equals(""))
        {
            address.setError(" Please enter your name");
            address.requestFocus();
            return false;
        }else if (phonenumber.getText().toString().equals("")) {
            phonenumber.setError("Please enter your phone number");
            phonenumber.requestFocus();
            return false;
        }else if(phonenumber.length() < 10){
            phonenumber.setError("Please enter valid phone number");
            phonenumber.requestFocus();
            return  false;
        }else if (Stremail.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your  email address", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return false;

        }else if (!Stremail.matches(emailPattern)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Log.i("EmailCheck","It is valid");
            return false;

        }
        return true;
    }


}