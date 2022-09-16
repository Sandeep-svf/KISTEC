package com.in.kistec.SettingsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.Registration_Model;
import com.in.kistec.API_Model.Update_Profile_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.Activity.Permission;
import com.in.kistec.LoginActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SignUpActivity.SignUpOtpActivity;
import com.in.kistec.SignupActivity;
import com.in.kistec.User.UserHomeActivity;
import com.in.kistec.User.splashActivity;


import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 4233;
    private static final int PICK_IMAGE2 = 4233;
    EditText setUserName, setUserEmail, setUserMobile , set_city_name;
    String userNameUpdate, userEmialUpdate, userMobileUpdate, authorization;
    CircleImageView setUserProfile;
    LinearLayout updateProifleLayout, uploadProfileAddButton, uoploadIDAddButton;
    private String UserID;
    ImageView setNationalID;
    File file;

    private ContentValues values6;

    private static final int PICK_IMAGE_R = 178500;
    private static final int PICK_IMAGE2_S = 20780;
    private static final int CAMERA_PIC_REQUEST = 308;
    private static final int CAMERA_PIC_REQUEST_R = 34198;
    private static final int CAMERA_PIC_REQUEST_S = 37798;
    private static final int CAMERA_PIC_REQUEST2 = 408;
    private static final int CAMERA_REQUEST_CODE = 9500;
    File profileImage = null, idImage;
    private String nameData, emailData, mobileData ,CityNameData;
    ImageView backArrowUpdate;
    private static final String IMAGE_DIRECTORY = "MyFolder/KISTEC";
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    private int keyfile1 = 1, keyfile2 = 2;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private Uri uriFilePath;
    private Bitmap thumbnail;
    private String imageAbsolutePath;
    private String imageName;
    private File photo;
    private ContentValues values;
    private Uri imageUri;
    private Uri imageUri2;
    private Bitmap thumbnail2;
    private MultipartBody.Part filePart1;
    private MultipartBody.Part filePart;
    private ContentValues values2;
    private File image;
    private File tempFile;
    private Uri img;
    private File file2;
    ImageView profile_background;
    private Uri imageUri6;
    private Bitmap thumbnail6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setUserMobile = findViewById(R.id.setUserMobile);
        setUserEmail = findViewById(R.id.setUserEmail);
        setUserName = findViewById(R.id.setUserName);
        updateProifleLayout = findViewById(R.id.updateProifleLayout);
        setUserProfile = findViewById(R.id.setUserProfile);
        uploadProfileAddButton = findViewById(R.id.uploadProfileAddButton);
        backArrowUpdate = findViewById(R.id.backArrowUpdate);
        set_city_name = findViewById(R.id.set_city_name);
        profile_background = findViewById(R.id.profile_background);

        Glide.with(MyProfileActivity.this).load(R.drawable.splash_background).into(profile_background);

        //geting userID data
        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");


        backArrowUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });

        uploadProfileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog addProfileUpdate = new Dialog(MyProfileActivity.this);
                addProfileUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addProfileUpdate.setContentView(R.layout.add_profile_update_dialog);
                TextView gallaryDialog = addProfileUpdate.findViewById(R.id.gallaryDialog);
                TextView cameraDialog = addProfileUpdate.findViewById(R.id.cameraDialog);

                addProfileUpdate.show();
                Window window = addProfileUpdate.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                gallaryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* openGallery(PICK_IMAGE);*/
                        gallery();
                        addProfileUpdate.dismiss();
                    }
                });

                cameraDialog.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {


                /*       PackageManager packageManager = MyProfileActivity.this.getPackageManager();
                        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
                        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
                        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

                        if ( camera && writeExternal && readExternal) {
                            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri = getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, CAMERA_PIC_REQUEST);
                                addProfileUpdate.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "camera permission required", Toast.LENGTH_SHORT).show();
                            // dialog fo  ask ermisson from user
                            //       if (ContextCompat.checkSelfPermission(MyProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "camera permission required", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                            addProfileUpdate.dismiss();
                        }*/

                        profile_camera_open();
                        addProfileUpdate.dismiss();

                    }
                });
            }
        });

       /* uoploadIDAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("test", "dialog_call_pass");

                Dialog addIdUpdateDialog = new Dialog(MyProfileActivity.this);
                addIdUpdateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addIdUpdateDialog.setContentView(R.layout.add_id_update_dialog);
                TextView gallaryDialog = addIdUpdateDialog.findViewById(R.id.gallaryDialog);
                TextView cameraDialog = addIdUpdateDialog.findViewById(R.id.cameraDialog);

                addIdUpdateDialog.show();
                Window window = addIdUpdateDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                gallaryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      *//*  openGallery(PICK_IMAGE2);*//*
                        Log.e("test", "open_galary_call_pass");

                        open_galary();
                        addIdUpdateDialog.dismiss();

                    }
                });


                cameraDialog.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                    *//*    PackageManager packageManager = MyProfileActivity.this.getPackageManager();

                        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
                        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
                        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

                        if (camera && writeExternal && readExternal) {
                            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

//                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST2);

                                values2 = new ContentValues();
                                values2.put(MediaStore.Images.Media.TITLE, "New Picture");
                                values2.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri2 = getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values2);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri2);
                                startActivityForResult(intent, CAMERA_PIC_REQUEST2);

                                addIdUpdateDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "camera permission required", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

                            addIdUpdateDialog.dismiss();
                        }*//*
                        id_camera_open();
                        addIdUpdateDialog.dismiss();
                    }
                });

            }
        });*/

        // User Details API
        get_user_details_api();

        updateProifleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validation field which can not be empty
                String email = setUserEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (setUserName.getText().toString().trim().equalsIgnoreCase("")) {
                    setUserName.setError("This field can not be blank");
                    setUserName.requestFocus();
                    return;
                }else if (setUserMobile.getText().toString().trim().equalsIgnoreCase("")) {
                    setUserMobile.setError("This field can not be blank");
                    setUserMobile.requestFocus();
                    return;
                }else if(setUserMobile.getText().toString().length() != 10)
                {
                    Toast.makeText(MyProfileActivity.this, "Please enter 10 digit valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }else if (setUserEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    setUserEmail.setError("This field can not be blank");
                    setUserEmail.requestFocus();
                    return;
                }else if(set_city_name.getText().toString().trim().equalsIgnoreCase("")) {
                    set_city_name.setError("This field can not be blank");
                    set_city_name.requestFocus();
                    return;
                }else if (! email.matches(emailPattern)) {
                    // Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    setUserEmail.setError("Invalid Email Address");
                    setUserEmail.requestFocus();
                    return;

                } else {
                    //  Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    nameData = setUserName.getText().toString();
                    mobileData = setUserMobile.getText().toString();
                    emailData = setUserEmail.getText().toString();
                    CityNameData = set_city_name.getText().toString();

                    update_profiel_api();
                }
            }
        });
    }


    private void open_galary()  {

        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

        if (readExternal && camera && writeExternal) {
            Log.e("test", "open_galary_reach_under_condition_pass");

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE2_S);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }


    private void gallery() {

        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);
        if (readExternal && camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_R);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void id_camera_open() {
        PackageManager packageManager = getApplicationContext().getPackageManager();

            boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
            boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
            boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

            if (readExternal && camera && writeExternal) {
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
                    if (!mainDirectory.exists())
                        mainDirectory.mkdirs();
                    Calendar calendar = Calendar.getInstance();
                    uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST_S);
                }else
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                }




        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void profile_camera_open() {

        PackageManager packageManager = MyProfileActivity.this.getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

        if (camera && writeExternal && readExternal ) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                values6 = new ContentValues();
                values6.put(MediaStore.Images.Media.TITLE, "New Picture");
                values6.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri6 = MyProfileActivity.this.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values6);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri6);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);
            }
        } else {
            Toast.makeText(MyProfileActivity.this, "camera permission required", Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        }
    }

     /*   PackageManager packageManager = getApplicationContext().getPackageManager();

        boolean readExternal = Permission.checkPermissionReadExternal(MyProfileActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(MyProfileActivity.this);
        boolean camera = Permission.checkPermissionCamera(MyProfileActivity.this);

        if (readExternal && camera && writeExternal) {
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                File mainDirectory = new File(Environment.getExternalStorageDirectory(), "MyFolder/tmp");
                if (!mainDirectory.exists())
                    mainDirectory.mkdirs();
                Calendar calendar = Calendar.getInstance();
                uriFilePath = Uri.fromFile(new File(mainDirectory, "IMG" + calendar.getTimeInMillis() + ".jpg"));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFilePath);
                startActivityForResult(intent, CAMERA_PIC_REQUEST_R);
            }else
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }*/

    private void camera(int cameraPicRequest) {

    }

    private void update_profiel_api() {

        Log.e("CityNameData"  , CityNameData);

        RequestBody UserNameData = RequestBody.create(MediaType.parse("text/plain"), nameData);
        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), UserID);
        RequestBody Authorization = RequestBody.create(MediaType.parse("text/plain"), authorization);
        RequestBody UserEmailData = RequestBody.create(MediaType.parse("text/plain"), emailData);
        RequestBody UserMobileData = RequestBody.create(MediaType.parse("text/plain"), mobileData);
        RequestBody cityNameData = RequestBody.create(MediaType.parse("text/plain"), CityNameData);


        if (profileImage == null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), "");
            filePart = MultipartBody.Part.createFormData("image", "", RequestBody.create(MediaType.parse("image/*"), ""));


        } else {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), profileImage);
            filePart = MultipartBody.Part.createFormData("image", profileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profileImage));
            Log.e("Photo", String.valueOf(profileImage));
        }


        if (idImage == null) {

            filePart1 = MultipartBody.Part.createFormData("nationalId", "", RequestBody.create(MediaType.parse("image/*"), ""));


        } else {


            filePart1 = MultipartBody.Part.createFormData("nationalId", idImage.getName(), RequestBody.create(MediaType.parse("image/*"), idImage));
            Log.e("Photo", String.valueOf(profileImage));
        }


//        Log.e("documentData", String.valueOf(profileImage));
//        try {
//            File file = new File(idImage.getPath());
//            RequestBody reportBody = RequestBody.create(MediaType.parse("image/*"), file);
//        } catch (Exception e) {
//            Log.v("dahgsdhjgdfhjs", "***********************************************" + e);
//            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
//        }
        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Update_Profile_Model> call = API_Client.getClient().update_profile(authorization,
                userID,
                UserNameData,
                UserMobileData,
                UserEmailData,
                cityNameData,
                filePart);

        call.enqueue(new Callback<Update_Profile_Model>() {
            @Override
            public void onResponse(Call<Update_Profile_Model> call, Response<Update_Profile_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            get_user_details_api();

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
                            Log.e("conversion issue", e.getMessage());
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    Log.e("conversion issue", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Update_Profile_Model> call, Throwable t) {
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

    private void get_user_details_api() {
        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<User_Details_Model> call = API_Client.getClient().user_details(authorization, UserID);

        call.enqueue(new Callback<User_Details_Model>() {
            @Override
            public void onResponse(Call<User_Details_Model> call, Response<User_Details_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        if (success.equals("true") || success.equals("True")) {

                            User_Details_Model user_details_model = response.body();
                            User_Detials_Result user_detials_result = user_details_model.getData();

                            userNameUpdate = user_detials_result.getName();
                            userEmialUpdate = user_detials_result.getEmail();
                            userMobileUpdate = user_detials_result.getMobile();
                           set_city_name.setText( user_detials_result.getCityName());


                            setUserName.setText(userNameUpdate);
                            setUserEmail.setText(userEmialUpdate);
                            setUserMobile.setText(userMobileUpdate);
                            Glide.with(getApplicationContext()).load(API_Client.BASE_IMAGE + user_detials_result.getProfile()).into(setUserProfile);
                            Glide.with(getApplicationContext()).load(API_Client.BASE_IMAGE_DOC + user_detials_result.getNationalId()).into(setNationalID);
//                            Log.v("fjdfjdf","***********"+userNameUpdate+""+userEmialUpdate+""+userMobileUpdate);


//                            SharedPreferences getUserIdData = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = getUserIdData.edit();
//                            editor.putString("UserID", String.valueOf(UserID));
//                            editor.apply();

//                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
//                            startActivity(intent);
//                            finish();

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
            public void onFailure(Call<User_Details_Model> call, Throwable t) {
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


    private void openGallery(int pickImage) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, pickImage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("test", "requestCode :" + requestCode+"  "+resultCode);
        Log.e("test", "PICK_IMAGE2_S :" + PICK_IMAGE2_S);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_R) {

                img = data.getData();

                String sel_path = getpath(img);

                try {

                    if (sel_path == null) {
                        Toast.makeText(getApplicationContext(), "Bad image it can not be selected", Toast.LENGTH_SHORT).show();
                    } else {

                        Log.d("selectedImagePath", sel_path);
                        final File filePath = new File(sel_path);
                        Bitmap bitmap = BitmapFactory.decodeFile(sel_path);
                        file = savebitmap(bitmap);
                        profileImage = saveBitmapToFile(filePath);

                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(filePath)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        setUserProfile.setImageBitmap(resource);
                                    }
                                });
                    }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "bad image", Toast.LENGTH_SHORT).show();
                }

        }
     else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE2_S) {

            Log.e("test", "result_ok_pass");


                Log.e("test", "code_pass************************************************");
                img = data.getData();

                String sel_path = getpath(img);

                try {
                    Log.e("test", "try_block_pass");

                    if (sel_path == null) {
                        Toast.makeText(getApplicationContext(), "Bad image it can not be selected", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("test", "selected_path_pass");

                        Log.d("selectedImagePath", sel_path);
                        final File filePath = new File(sel_path);
                        Bitmap bitmap = BitmapFactory.decodeFile(sel_path);
                        file2 = savebitmap(bitmap);
                        idImage = saveBitmapToFile(filePath);


                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(filePath)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                        setNationalID.setImageBitmap(resource);
                                    }
                                });
                    }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "bad image", Toast.LENGTH_SHORT).show();
                }
        }

    else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profileImage));
            String selectedImagePath1 = getPath(selectedImageUri);
            //        Picasso.get().load(new File(String.valueOf(profileImage))).resize(3000,4000).into(setUserProfile);
            Glide.with(getApplicationContext()).load(selectedImagePath1).into(setUserProfile);

        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE2) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            idImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(idImage));
            String selectedImagePath2 = getPath(selectedImageUri);
//            Picasso.get().load(new File(String.valueOf(idImage))).resize(3000,4000).into(setNationalID);
            Glide.with(getApplicationContext()).load(selectedImagePath2).into(setNationalID);

        } else if (requestCode == CAMERA_PIC_REQUEST2 && resultCode == RESULT_OK) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            setNationalID.setImageBitmap(thumbnail);
//            saveImage(thumbnail, keyfile2);
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri2);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(getRealPathFromURI(imageUri2));

//            Bitmap bitmap = flipping(thumbnail);
//            setNationalID.setImageBitmap(bitmap);
//            setNationalID.setRotation(90

            Glide.with(MyProfileActivity.this)
                    .load(file)
                  .placeholder(R.drawable.ic_launcher_background)
                    .into(setNationalID);

            //     setNationalID.setRotation(90);
            // compress file before send to server
            //  Picasso.get().load(new File(selectedImagePath9)).resize(1000,1000).into((Target) profileImage);
            // Getting Real uri path
            idImage = new File(getRealPathFromURI(imageUri2));

            //idImage =  saveBitmapToFile(thumbnail);

        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap2;
            try {

                thumbnail2 = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            File file = new File(getRealPathFromURI(imageUri));
            File file2 =  saveBitmapToFile(file);
            String fileName = null;

            Glide.with(MyProfileActivity.this)
                    .load(file2)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(setUserProfile);
            profileImage = file2;


        }else if ( requestCode == CAMERA_PIC_REQUEST_R && resultCode == RESULT_OK )
        {
          //  Toast.makeText(this, "R Code Working", Toast.LENGTH_SHORT).show();


            try {
                thumbnail6 = MediaStore.Images.Media.getBitmap(
                        MyProfileActivity.this.getContentResolver(), imageUri6);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri6));

            Glide.with(MyProfileActivity.this)
                    .load(file)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(setUserProfile);

            // Glide.with(getActivity()).load(thumbnail5).into(addRecordProfile);
            // Picasso.get().load(String.valueOf(thumbnail)).into(setUserProfile);
            // compress file before send to server
            //  Picasso.get().load(new File(selectedImagePath9)).resize(1000,1000).into((Target) profileImage);
            // Getting Real uri path
            profileImage  = new File(getRealPathFromURIs(imageUri6));

           /* try {
                ExifInterface exif = new ExifInterface(uriFilePath.getPath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                BitmapFactory.Options options = new BitmapFactory.Options();//
                options.inSampleSize = 2;//
                thumbnail = BitmapFactory.decodeFile(uriFilePath.getPath(), options);//

                Matrix matrix = new Matrix();

                thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),//
                        thumbnail.getHeight(), matrix, true);//

                 file = savebitmap(thumbnail);
                Log.d(" camera Path*********", uriFilePath.getPath());//
                String imageNamePath = uriFilePath.getPath();//
                imageAbsolutePath = imageNamePath;//
                String[] separated = imageNamePath.split("/");//
                imageName = separated[separated.length - 1];//

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;

                }
                String uri = uriFilePath.getPath();
                setUserProfile.setImageBitmap(thumbnail);


                File temp_file = new File(new URI("file://" + uri.replace(" ", "%20")));
                profileImage = saveBitmapToFile(temp_file);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        }else  if (requestCode == CAMERA_PIC_REQUEST_S)
        {
            try {
                ExifInterface exif = new ExifInterface(uriFilePath.getPath());
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);

                BitmapFactory.Options options = new BitmapFactory.Options();//
                options.inSampleSize = 2;//
                thumbnail = BitmapFactory.decodeFile(uriFilePath.getPath(), options);//

                Matrix matrix = new Matrix();

                thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),//
                        thumbnail.getHeight(), matrix, true);//

                file = savebitmap(thumbnail);
                Log.d(" camera Path*********", uriFilePath.getPath());//
                String imageNamePath = uriFilePath.getPath();//
                imageAbsolutePath = imageNamePath;//
                String[] separated = imageNamePath.split("/");//
                imageName = separated[separated.length - 1];//

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        matrix.postRotate(90);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        matrix.postRotate(180);
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        matrix.postRotate(270);
                        break;

                }
                String uri = uriFilePath.getPath();
                setNationalID.setImageBitmap(thumbnail);


                File temp_file2 = new File(new URI("file://" + uri.replace(" ", "%20")));
                idImage = saveBitmapToFile(temp_file2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public File createImageFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
// ************************************************************ METHOD STARTED ****************************************************




    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
                                                     int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);


    }

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

/*    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "testimage.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }*/


/*    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + "testimage.jpg");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }*/

    public String saveImage(Bitmap myBitmap, int key) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists())  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();

        Toast.makeText(getApplicationContext(), "****** Directory created *******", Toast.LENGTH_SHORT).show();


        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            Toast.makeText(getApplicationContext(), "****** Reaching last statement *******", Toast.LENGTH_SHORT).show();

            if (key == keyfile1) {

                Uri uripares = Uri.fromFile(new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg"));

            } else if (key == keyfile2) {

                idImage = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            }


            Log.e("camerea_image_path", String.valueOf(profileImage));
            Log.e("camerea_image_path", String.valueOf(idImage));

            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //  inImage.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
        Bitmap.createScaledBitmap(inImage, 1000, 1000, true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public  File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            Toast.makeText(MyProfileActivity.this, "*********File did not compressed************", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bitmap flipping(Bitmap b)
    {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
        try {

            ExifInterface exif = new ExifInterface(bs);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotateImage(b, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotateImage(b, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotateImage(b, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:

                default:
                    break;
            }


//            encoding();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }



    private void persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(MyProfileActivity.class.getSimpleName(), "Error writing bitmap", e);
        }
    }


    public static File bitmapToFile(Context context,Bitmap bitmap, String fileNameToSave)
    {
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }



    public  String getpath(Uri img) {
        String[] p = {MediaStore.Images.Media.DATA};
        Cursor cursor = getApplication().getContentResolver().query(img, p, null, null, null);
        int col = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String file_path = cursor.getString(col);

        try {
            photo = new File(new URI("file://" + file_path.replace(" ", "%20")));
            // Toast.makeText(this, "ajendra "+photo, Toast.LENGTH_SHORT).show();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return cursor.getString(col);
    }
        public static File savebitmap(Bitmap bmp) throws IOException {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "testimage.jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            return f;
        }

}




