package com.in.kistec.NotificationActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.API_Result_Data.Notification_Result;
import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.Notification_Model;

import com.in.kistec.Adapter.StatusListAdapter;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.User.UserHomeActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
   /* private String UserID, authorization;
    List<Notification_Result> list=new ArrayList<>();
    RecyclerView rcvNotification;
    Notofication_Adapter notofication_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        rcvNotification = findViewById(R.id.rcvNotification);



        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        rcvNotification.setLayoutManager(linearLayoutManager2);



        //geting userID data
        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        notification_api();

    }

    private void notification_api() {
        // show till load api data
        Toast.makeText(NotificationActivity.this, "Entry Point 1", Toast.LENGTH_SHORT).show();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Notification_Model> call = API_Client.getClient().get_notfication(authorization, UserID);

        call.enqueue(new Callback<Notification_Model>() {
            @Override
            public void onResponse(Call<Notification_Model> call, Response<Notification_Model> response) {
                pd.dismiss();
                Toast.makeText(NotificationActivity.this, "Entry Point 2", Toast.LENGTH_SHORT).show();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            list = response.body().getData();
                            Toast.makeText(NotificationActivity.this, "Inside Success", Toast.LENGTH_SHORT).show();

                            Log.e("list_size", ": AAAAAAAAAA" +list.size());

                            *//*statusListAdapter = new StatusListAdapter( list,getActivity());
                            recSubmitStatus.setAdapter(statusListAdapter);
*//*
                            notofication_adapter = new Notofication_Adapter(list, NotificationActivity.this);
                            rcvNotification.setAdapter(notofication_adapter);


                        } else {
                            Toast.makeText(getApplicationContext(), message + "\n" + success + "\n" + "Please Try Again", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Notification_Model> call, Throwable t) {
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
    }*/
}