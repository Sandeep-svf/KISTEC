package com.in.kistec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;
import com.in.kistec.API_Model.Registration_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.Activity.Permission;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SettingsActivity.MyProfileActivity;
import com.in.kistec.SettingsActivity.TermAndConditionActivity;
import com.in.kistec.SignUpActivity.SignUpOtpActivity;
import com.in.kistec.User.splashActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    //###########################################################

   // I have already registered.You can login with the login credentials I have given of you And this login credentials will not be expired And that's valid demo credentials.Otherwise you can also register with SMS Verification. The app typically requires a 2-Step Verification code or One Time Password.

    private static final int ITERATION_COUNT = 1000;
    private static final int KEY_LENGTH = 256;
    private static final String PBKDF2_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int PKCS5_SALT_LENGTH = 32;
    private static final String DELIMITER = "]";
    private static final SecureRandom random = new SecureRandom();
    ViewFlipper viewFlipper;

    //###########################################################

    private static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE2 = 200;
    private static final int CAMERA_PIC_REQUEST = 137;
    private static final int CAMERA_PIC_REQUEST2 = 1377;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_RS = 45456;
    private static final int PICK_IMAGE_RS2 = 48556;
    private static final int CAMERA_PIC_REQUEST_GS = 4756;
    boolean checkbox = false;
    LinearLayout signupHomeLayout, uoploadIDAddButton, addUserProfileSignup;
    EditText userPassword, userMobileNumber, userName, userEmail, userConfirmPassword, city_name;
    String userNameData, userEmailData, userMobileData, userPasswordData, userConfirmPasswordData;
    CheckBox tcCheckBox;
    CircleImageView userProfileImage;
    File profileImage, idImage;
    Uri imageUri;
    String cityName;
    private Uri img;
    TextView term_and_condition;
    ImageView addImageID, signin_password_eye, signin_password_hidden,
            sign_in_confirm_password_eye, sign_in_confirm_password_hidden, singup_background;
    private Object file;
    private Uri imageUri3;
    ImageView otp_logo, otp_background;
    private File photo;
    private Uri imageUri4;
    private Bitmap thumbnail3;
    private Bitmap thumbnail4;
    TextView addImageText, sign_in_layout, verify_layout;
    private ContentValues values3;
    private ContentValues values4;
    private ProgressDialog progressDialog;
    CountryCodePicker ccp;

    private String verificationId;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
            verificationId = s;
            viewFlipper.setDisplayedChild(1);
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
            String code = phoneAuthCredential.getSmsCode();
            signInWithCredential(phoneAuthCredential);
            if (code != null) {
//                editText.setText(code);
//                verifyCode(code);
                if (!(code.equals(""))) {
                    char a = code.charAt(0);
                    char b = code.charAt(1);
                    char c = code.charAt(2);
                    char d = code.charAt(3);
                    char e = code.charAt(4);
                    char f = code.charAt(5);
                }
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("onVerificationFailed", e + "");
            if (progressDialog != null && progressDialog.isShowing())
                Toast.makeText(SignupActivity.this, "OTP Verification failed. Please enter valid phone number and try again.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    };
    private Uri uriFilePath;
    private Bitmap thumbnail2;
    private String string_phone;
    EditText phoneText, numOne, numTwo, numThree, numFour, numFive, numSix;
    private String phoneVerificationId;
    private String newPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        signupHomeLayout = findViewById(R.id.signupHomeLayout);
        tcCheckBox = findViewById(R.id.tcCheckBox);
        sign_in_layout = findViewById(R.id.sign_in_layout);
        userPassword = findViewById(R.id.userPassword);
        userMobileNumber = findViewById(R.id.userMobileNumber);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        viewFlipper = findViewById(R.id.view_fkip);
        userConfirmPassword = findViewById(R.id.userConfirmPassword);
        verify_layout = findViewById(R.id.verify_layout);
        signin_password_eye = findViewById(R.id.signin_password_eye);
        signin_password_hidden = findViewById(R.id.signin_password_hidden);
        sign_in_confirm_password_eye = findViewById(R.id.sign_in_confirm_password_eye);
        sign_in_confirm_password_hidden = findViewById(R.id.sign_in_confirm_password_hidden);
        term_and_condition = findViewById(R.id.term_and_condition);
        singup_background = findViewById(R.id.singup_background);
        otp_logo = findViewById(R.id.otp_logo);
        ccp = findViewById(R.id.ccp);
        city_name = findViewById(R.id.city_name);
        otp_background = findViewById(R.id.otp_background);

        Glide.with(SignupActivity.this).load(R.drawable.splash_background).into(singup_background);
        Glide.with(SignupActivity.this).load(R.drawable.splash_background).into(otp_background);

        Glide.with(SignupActivity.this).load(R.drawable.logo_final).into(otp_logo);

        term_and_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermAndConditionActivity.class);
                startActivity(intent);
            }
        });
        sign_in_confirm_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userConfirmPassword.setTransformationMethod(null);
                sign_in_confirm_password_eye.setVisibility(View.GONE);
                sign_in_confirm_password_hidden.setVisibility(View.VISIBLE);
                userConfirmPassword.setSelection(userConfirmPassword.getText().length());

            }
        });

        sign_in_confirm_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                sign_in_confirm_password_eye.setVisibility(View.VISIBLE);
                sign_in_confirm_password_hidden.setVisibility(View.GONE);
                userConfirmPassword.setSelection(userConfirmPassword.getText().length());

            }
        });

        signin_password_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setTransformationMethod(null);
                signin_password_eye.setVisibility(View.GONE);
                signin_password_hidden.setVisibility(View.VISIBLE);
                userPassword.setSelection(userPassword.getText().length());
            }
        });

        signin_password_hidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPassword.setTransformationMethod(new PasswordTransformationMethod());
                signin_password_eye.setVisibility(View.VISIBLE);
                signin_password_hidden.setVisibility(View.GONE);
                userPassword.setSelection(userPassword.getText().length());
            }
        });


        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait...");


        // ##############################################################################################################################
        String encryptedPassword = encrypt("Sandeep", "12345");
        String dncryptedPassword = decrypt(encryptedPassword, "12345");


        Log.e("enPassword", "enPassword is :" + encryptedPassword);
        Log.e("enPassword", "dePassword is :" + dncryptedPassword);

        // ##############################################################################################################################



        sign_in_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tcCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcCheckBox.isChecked()) {
                    checkbox = true;
                }
            }
        });

        codenumber();
        verify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCodes(v);
            }
        });

        signupHomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityName = city_name.getText().toString();
                String email = userEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (userName.getText().toString().trim().equalsIgnoreCase("")) {
                    userName.setError("This field can not be blank");
                    userName.requestFocus();
                    return;
                } else if (userEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    userEmail.setError("This field can not be blank");
                    userEmail.requestFocus();
                    return;
                } else if (!email.matches(emailPattern)) {
                    userEmail.setError("Please enter valid email Address");
                    userEmail.requestFocus();
                    return;

                } else if (userMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    Log.e("Running", "Please enter phone number");
                    userMobileNumber.setError("This field can not be blank");
                    userMobileNumber.requestFocus();
                    return;
                } else if( (userMobileNumber.getText().toString().length()  < 8) || (userMobileNumber.getText().toString().length()  >11)) {
                    Toast.makeText(SignupActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    return;
                } else if (city_name.getText().toString().trim().equalsIgnoreCase("")) {
                    city_name.setError("This field can not be blank");
                    city_name.requestFocus();
                    return;
                } else if (userPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    userPassword.setError("This field can not be blank");
                    userPassword.requestFocus();
                    return;

                } else if (userPassword.getText().toString().length() < 6) {
                    userPassword.setError("Password cannot be less than 6 characters");
                    userPassword.requestFocus();
                    return;

                } else if (userConfirmPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    userConfirmPassword.setError("This field can not be blank");
                    userConfirmPassword.requestFocus();
                    return;
                } else if (!userPassword.getText().toString().equals(userConfirmPassword.getText().toString())) {
                    userConfirmPassword.setError("Password did not match");
                    userConfirmPassword.requestFocus();
                    return;
                } else if (!tcCheckBox.isChecked()) {

                    Toast errorToast = Toast.makeText(getApplicationContext(), "Please check terms and conditions and try again!", Toast.LENGTH_SHORT);
                    errorToast.show();
                    return;

                } else {
                    // Taking data from user in String
                    userNameData = userName.getText().toString();
                    userEmailData = userEmail.getText().toString();
                    userMobileData = userMobileNumber.getText().toString();
                    userPasswordData = userPassword.getText().toString();
                    userConfirmPasswordData = userConfirmPassword.getText().toString();

                    nextbtn(viewFlipper);



                }
            }
        });
    }


    public void verifyCodes(View view) {
        String code = "" + numOne.getText().toString() + numTwo.getText().toString() + numThree.getText().toString() + numFour.getText().toString() + numFive.getText().toString() + numSix.getText().toString();
        // Toast.makeText(SignupActivity.this, code, Toast.LENGTH_SHORT).show();
        if (!code.equals("")) {

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(verificationId, code);

            signInWithCredential(credential);
        } else {
            Toast.makeText(this, "Enter the Correct verification Code", Toast.LENGTH_SHORT).show();
        }

    }

    public void codenumber() {
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
                if (numFive.getText().toString().length() == 0) {
                    numSix.requestFocus();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numFive.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        numSix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (numFive.getText().toString().length() == 0) {
                    numFour.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void nextbtn(View view) {

        String temp = userMobileData.substring(0, 1);

        if (temp.equals("0")) {
            userMobileData = userMobileData.substring(1, userMobileData.length());
        } else {
            userMobileData = userMobileData;
        }

        Log.e("check", userMobileData);

        String fbUserPhoneNumber = "";

        String countryCode = ccp.getSelectedCountryCode();
        if (userMobileData.length() == 9 || userMobileData.length() == 10) {
            //fbUserPhoneNumber = "+1" + userMobileData;
            fbUserPhoneNumber = "+"+countryCode + userMobileData;
            sendVerificationCode(fbUserPhoneNumber);

            Log.e("check", "Complete phone number: "+fbUserPhoneNumber);

        } else {
            Toast toast = Toast.makeText(SignupActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void open_galary_iImage() {

        boolean readExternal = Permission.checkPermissionReadExternal(SignupActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(SignupActivity.this);
        boolean camera = Permission.checkPermissionCamera(SignupActivity.this);
        if (readExternal && camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_RS2);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    private void open_galary_profile() {
        boolean readExternal = Permission.checkPermissionReadExternal(SignupActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(SignupActivity.this);
        boolean camera = Permission.checkPermissionCamera(SignupActivity.this);
        if (readExternal && camera) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_RS);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }

        }

    }

    private void sendVerificationCode(String number) {
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // After successful OTP using sign up API
                            registration_api();

                            Toast.makeText(getApplicationContext(), "OTP verification successfull", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Wrong OTP !", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void verifyCode(String code) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//        progressBar.setVisibility(View.VISIBLE);
            progressDialog.show();
            signInWithCredential(credential);

        } catch (Exception e) {

            Log.e("hgkjhdfk", e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage() + "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void registration_api() {

//        String userMobileNumber = "+1" + newPhoneNumber;
//        String withoutConryCodeNumber = "";
//        if (userMobileNumber.substring(0,3).equals("+91")){
//            withoutConryCodeNumber = userMobileNumber;
//        }

        RequestBody UserNameData = RequestBody.create(MediaType.parse("text/plain"), userNameData);
        RequestBody UserEmailData = RequestBody.create(MediaType.parse("text/plain"), userEmailData);
        RequestBody UserMobileData = RequestBody.create(MediaType.parse("text/plain"), userMobileData);
        RequestBody UserPasswordData = RequestBody.create(MediaType.parse("text/plain"), userPasswordData);
        RequestBody city_name = RequestBody.create(MediaType.parse("text/plain"), cityName);
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", profileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profileImage));
//        MultipartBody.Part filePart1 = MultipartBody.Part.createFormData("nationalId", idImage.getName(), RequestBody.create(MediaType.parse("image/*"), idImage));

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Registration_Model> call = API_Client.getClient().registration(UserNameData, UserEmailData, UserMobileData,
                UserPasswordData, city_name);

        call.enqueue(new Callback<Registration_Model>() {
            @Override
            public void onResponse(Call<Registration_Model> call, Response<Registration_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = response.body().getSuccess();

                        if (success.equals("true") || success.equals("True")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), message + "\n" + "Please Try Again", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                            startActivity(intent);
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
            public void onFailure(Call<Registration_Model> call, Throwable t) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_RS) {

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
                                    userProfileImage.setImageBitmap(resource);
                                }
                            });
                }
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "bad image", Toast.LENGTH_SHORT).show();
            }


        } else if (requestCode == CAMERA_PIC_REQUEST_GS && resultCode == RESULT_OK) {
            Bitmap bitmap2;
            try {

                thumbnail2 = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }

            File file = new File(getRealPathFromURI(imageUri));
            File file2 = saveBitmapToFile(file);
            String fileName = null;

            Glide.with(SignupActivity.this)
                    .load(file2)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(userProfileImage);
            profileImage = file2;

        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_RS2) {

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
                    idImage = saveBitmapToFile(filePath);

                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(filePath)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    addImageID.setImageBitmap(resource);
                                }
                            });
                }
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "bad image", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profileImage));
            String selectedImagePath1 = getPath(selectedImageUri);
            Glide.with(getApplicationContext()).load(selectedImagePath1).into(userProfileImage);
        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE2) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            idImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(idImage));
            String selectedImagePath2 = getPath(selectedImageUri);
            Glide.with(getApplicationContext()).load(selectedImagePath2).into(addImageID);
            addImageText.setVisibility(View.GONE);
            addImageID.setVisibility(View.VISIBLE);
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {

            try {
                thumbnail3 = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);
            File file = new File(getRealPathFromURI(imageUri3));
            Glide.with(getApplicationContext()).load(file).into(userProfileImage);
            profileImage = new File(getRealPathFromURIs(imageUri3));

        } else if (requestCode == CAMERA_PIC_REQUEST2 && resultCode == RESULT_OK) {
            try {
                thumbnail4 = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri4);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // addImageID.setImageBitmap(thumbnail);
            File file = new File(getRealPathFromURI(imageUri4));

            Glide.with(getApplicationContext()).load(file).into(addImageID);
            addImageText.setVisibility(View.GONE);
            addImageID.setVisibility(View.VISIBLE);
            idImage = new File(getRealPathFromURI(imageUri4));
        }
    }

    // ******************************************************* METHOD **************************************************************

    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String getRealPathFromURI(Uri contentUri) {
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


    public String getpath(Uri img) {
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

    public File saveBitmapToFile(File file) {
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
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
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

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            Toast.makeText(SignupActivity.this, "*********File did not compressed************", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


//#####################################################################################################################################

    public static String encrypt(String plaintext, String password) {
        byte[] salt = generateSalt();
        SecretKey key = deriveKey(password, salt);

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            byte[] iv = generateIv(cipher.getBlockSize());
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
            byte[] cipherText = cipher.doFinal(plaintext.getBytes("UTF-8"));

            if (salt != null) {
                return String.format("%s%s%s%s%s",
                        toBase64(salt),
                        DELIMITER,
                        toBase64(iv),
                        DELIMITER,
                        toBase64(cipherText));
            }

            return String.format("%s%s%s",
                    toBase64(iv),
                    DELIMITER,
                    toBase64(cipherText));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String ciphertext, String password) {
        String[] fields = ciphertext.split(DELIMITER);
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid encypted text format");
        }
        byte[] salt = fromBase64(fields[0]);
        byte[] iv = fromBase64(fields[1]);
        byte[] cipherBytes = fromBase64(fields[2]);
        SecretKey key = deriveKey(password, salt);

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
            byte[] plaintext = cipher.doFinal(cipherBytes);
            return new String(plaintext, "UTF-8");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] generateSalt() {
        byte[] b = new byte[PKCS5_SALT_LENGTH];
        random.nextBytes(b);
        return b;
    }

    private static byte[] generateIv(int length) {
        byte[] b = new byte[length];
        random.nextBytes(b);
        return b;
    }

    private static SecretKey deriveKey(String password, byte[] salt) {
        try {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_DERIVATION_ALGORITHM);
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private static byte[] fromBase64(String base64) {
        return Base64.decode(base64, Base64.NO_WRAP);
    }
}