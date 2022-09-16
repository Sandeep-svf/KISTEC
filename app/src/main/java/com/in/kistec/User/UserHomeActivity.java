package com.in.kistec.User;

import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonIOException;
import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.Notification_Model;
import com.in.kistec.API_Model.Read_Notification_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.API_Model.notification_Count_Model;
import com.in.kistec.Fragment.AddRecordFragment;
import com.in.kistec.Fragment.HomeFragment;
import com.in.kistec.Fragment.ManageProfileFragment;
import com.in.kistec.Fragment.NotificatonFragment;
import com.in.kistec.Fragment.StatusFragment;
import com.in.kistec.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.kistec.Retrofit.API_Client;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {
    RecyclerView rcvPersonDetails;
    LinearLayout homeLayout, add_members_layout, view_status_layout, Settings_layout;
    private String userType, UserID, authorization;
    Context context;
    TextView headerName, noti_count_textview;
    LinearLayout bnv;
    FrameLayout notificationLayout;
    Fragment temp = null;
    ImageView home_icon, add_members_icon, view_status_icon, settings_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, new HomeFragment()).commit();

        bnv = findViewById(R.id.bottomNavigation);
        headerName = findViewById(R.id.headerName);
        noti_count_textview = findViewById(R.id.noti_count_textview);
        notificationLayout = findViewById(R.id.notificationLayout);
        homeLayout = findViewById(R.id.homeLayout);
        add_members_layout = findViewById(R.id.add_members_layout);
        view_status_layout = findViewById(R.id.view_status_layout);
        Settings_layout = findViewById(R.id.Settings_layout);
        home_icon = findViewById(R.id.home_icon);
        add_members_icon = findViewById(R.id.add_members_icon);
        view_status_icon = findViewById(R.id.view_status_icon);
        settings_icon = findViewById(R.id.settings_icon);

        //geting userID data
        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        Log.e("userId",UserID);

        notification_count_api();

        noti_count_textview.getText().toString().toLowerCase();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, homeFragment);
                fragmentTransaction.commit();

                headerName.setText("SEARCH PERSON");

                home_icon.setImageResource(R.drawable.search_icon_color);
                add_members_icon.setImageResource(R.drawable.add);
                view_status_icon.setImageResource(R.drawable.view);
                settings_icon.setImageResource(R.drawable.setting);
            }
        });


        add_members_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRecordFragment addRecordFragment = new AddRecordFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, addRecordFragment);
                fragmentTransaction.commit();

                headerName.setText("FILE CASE REPORT");

                home_icon.setImageResource(R.drawable.search_home);
                add_members_icon.setImageResource(R.drawable.add_color_icon);
                view_status_icon.setImageResource(R.drawable.view);
                settings_icon.setImageResource(R.drawable.setting);
            }
        });

        view_status_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusFragment statusFragment = new StatusFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, statusFragment);
                fragmentTransaction.commit();

                headerName.setText("VIEW STATUS");

                home_icon.setImageResource(R.drawable.search_home);
                add_members_icon.setImageResource(R.drawable.add);
                view_status_icon.setImageResource(R.drawable.view_color_icon);
                settings_icon.setImageResource(R.drawable.setting);
            }
        });

        Settings_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageProfileFragment manageProfileFragment = new ManageProfileFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                ((FrameLayout) findViewById(R.id.fragment_contaner)).removeAllViews();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_contaner, manageProfileFragment);
                fragmentTransaction.commit();
                headerName.setText("SETTINGS");

                home_icon.setImageResource(R.drawable.search_home);
                add_members_icon.setImageResource(R.drawable.add);
                view_status_icon.setImageResource(R.drawable.view);
                settings_icon.setImageResource(R.drawable.settings_color_icon);
            }
        });

/*
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // load fragment according to menu id
                // using temp fragment , No need to replace more than one time

                switch (item.getItemId())
                {
                    case R.id.menu_home : temp = new HomeFragment();
                        headerName.setText("Home");
                        break;
                    case R.id.menu_add_record: temp = new AddRecordFragment();
                        headerName.setText("Add Record");

                        break;
                    case R.id.menu_Status:  temp = new StatusFragment();
                        headerName.setText("Status");
                        break;

                    case R.id.menu_manage_profile:  temp = new ManageProfileFragment();
                        headerName.setText("Settings");
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, temp).commit();

                return true;
            }
        });
*/

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Read_Notification_Api();

            }
        });

        rcvPersonDetails = findViewById(R.id.rcvPersonDetails);
//        manageProfieLayout = findViewById(R.id.manageProfieLayout);
//        addRecordLayout = findViewById(R.id.addRecordLayout);
//        statusLayout = findViewById(R.id.statusLayout);

        //RECVING DATA PASSED VIA INTENT
//        userType = getIntent().getStringExtra("USER_TYPE");

//
//        statusLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(UserHomeActivity.this, StatusActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//
//        manageProfieLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(UserHomeActivity.this, ManageProfileActivity.class);
//                startActivity(intent);
//
//            }
//        });
//
//        addRecordLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Dialog dialog = new Dialog(UserHomeActivity.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.popup_layout);
//                TextView homeOnRent = dialog.findViewById(R.id.homeOnRent);
//                TextView lendMoney = dialog.findViewById(R.id.lendMoney);
//                dialog.show();
//
//
//                lendMoney.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Intent intent = new Intent(UserHomeActivity.this, AddRecordActivity.class);
//                        startActivity(intent);
//                    }
//                });
//
//                homeOnRent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        Intent intent = new Intent(UserHomeActivity.this, AddRecordActivity.class);
//                        startActivity(intent);
//                    }
//                });
//
//            }
//        });

    }

    private void Read_Notification_Api() {
        // show till load api data

        final ProgressDialog pd = new ProgressDialog(UserHomeActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        Log.e("notiication_count", "AA");

        Call<Read_Notification_Model> call = API_Client.getClient().readNotification(authorization, UserID, "1");
        Log.e("notiication_count", "BB");

        call.enqueue(new Callback<Read_Notification_Model>() {
            @Override
            public void onResponse(Call<Read_Notification_Model> call, Response<Read_Notification_Model> response) {

                try {
                    Log.e("notiication_count", "CC");
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());
                        Log.e("notiication_count", "DD");
                        pd.dismiss();

                        if (success.equals("true") || success.equals("True")) {
                            Log.e("notiication_count", "EE");

                            noti_count_textview.setText("0");
                            temp = new NotificatonFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contaner, temp).commit();
                            headerName.setText("Notifications");


                        } else {
                            Toast.makeText(UserHomeActivity.this, message + "\n" + success + "\n" + "Please Try Again", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(UserHomeActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(UserHomeActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(UserHomeActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(UserHomeActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(UserHomeActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(UserHomeActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(UserHomeActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(UserHomeActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(UserHomeActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(UserHomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Read_Notification_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(UserHomeActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(UserHomeActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }


    private void notification_count_api() {
        // show till load api data
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(UserHomeActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading...");
        progressDialog.hide();

        Call<notification_Count_Model> call = API_Client.getClient().noti_count(authorization, UserID);

        call.enqueue(new Callback<notification_Count_Model>() {
            @Override
            public void onResponse(Call<notification_Count_Model> call, Response<notification_Count_Model> response) {

                try {

                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());
                        progressDialog.dismiss();
                        if (success.equals("true") || success.equals("True")) {

                            notification_Count_Model notification_count_model = response.body();
                            String notification_count = String.valueOf(notification_count_model.getData());
                            Log.e("notification_value", "notification_value : " + notification_count);
                            noti_count_textview.setText(notification_count);

                        } else {
                            Toast.makeText(UserHomeActivity.this, message + "\n" + success + "\n" + "Please Try Again", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(UserHomeActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(UserHomeActivity.this, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(UserHomeActivity.this, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(UserHomeActivity.this, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(UserHomeActivity.this, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(UserHomeActivity.this, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(UserHomeActivity.this, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(UserHomeActivity.this, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(UserHomeActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(UserHomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (IllegalStateException e)
                {}
                catch (JsonIOException e)
                {}
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<notification_Count_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(UserHomeActivity.this, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(UserHomeActivity.this, "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });


    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
*/

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) return null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        notification_count_api();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
    }
}