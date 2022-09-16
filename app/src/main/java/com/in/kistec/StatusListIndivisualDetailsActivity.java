package com.in.kistec;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.Login_Result;
import com.in.kistec.API_Model.API_Result_Data.Record_Details_Result;
import com.in.kistec.API_Model.Edit_Record_Model;
import com.in.kistec.API_Model.Login_Model;
import com.in.kistec.API_Model.Record_Details_Model;
import com.in.kistec.Activity.Permission;
import com.in.kistec.Adapter.Add_Doc_Adapter;
import com.in.kistec.Adapter.Edit_Doc_Adapter;
import com.in.kistec.Adapter.Record_Details_Adapter_Image;
import com.in.kistec.Fragment.AddRecordFragment;
import com.in.kistec.ImportDocHelperClass.ImagesActivity;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SettingsActivity.MyProfileActivity;
import com.in.kistec.User.UserHomeActivity;
import com.in.kistec.User.splashActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusListIndivisualDetailsActivity extends AppCompatActivity {
    private String record_id;
    EditText editAboutUsDetails, personIdDetails, nameDetials, mobileDetails;
    CircleImageView userProfileDetails;
    private String UserID, authorization;
    RecyclerView recRecordImage, recRecordImage2;
    LinearLayout editRecordImportDocument, editRecordProfile, editRecordSubmitLayout;
    public static ArrayList<File> selectedImageList = new ArrayList<>();
    public static ArrayList<String> selectedImageList3 = new ArrayList<>();
    private static final int CAMERA_PIC_REQUEST = 800;
    private static final int PICK_IMAGE = 900;
    File profileImage;
    private String editNameData, editMobileData, editPersonIdData, spinPersonTypeData, PersonType, editAboutUsDetailsData;
    Spinner editSpinPersonType;

    MultipartBody.Part[] helpreportImagesParts;
    private ArrayList<File> Temp_test = new ArrayList<>();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    Uri destinoURI = null;
    File pictureFile = null;
    private String personType;
    ImageView status_details_background, backArrow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_list_indivisual_details);

        editAboutUsDetails = findViewById(R.id.editAboutUsDetails);
        personIdDetails = findViewById(R.id.personIdDetails);
        nameDetials = findViewById(R.id.nameDetials);
        mobileDetails = findViewById(R.id.mobileDetails);
        userProfileDetails = findViewById(R.id.userProfileDetails);
        recRecordImage = findViewById(R.id.recRecordImage);
        editRecordImportDocument = findViewById(R.id.editRecordImportDocument);
        editRecordProfile = findViewById(R.id.editRecordProfile);
        editRecordSubmitLayout = findViewById(R.id.editRecordSubmitLayout);
        editSpinPersonType = findViewById(R.id.editSpinPersonType);
        recRecordImage2 = findViewById(R.id.recRecordImage2);
        backArrow = findViewById(R.id.backArrow);
        status_details_background = findViewById(R.id.status_details_background);

        Glide.with(StatusListIndivisualDetailsActivity.this).load(R.drawable.splash_background).into(status_details_background);


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recRecordImage2.setLayoutManager(linearLayoutManager2);

        spinPerson(0);


       /* if(persontype.equals("0"))
        {
            personType = "Rent Money";
        }else
        {
            personType = "Rent House";
        }*/

        /* editSpinPersonType.s*/
        backArrow.setOnClickListener(v -> finish());

        editRecordProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog editProfileUpdate = new Dialog(StatusListIndivisualDetailsActivity.this);
                editProfileUpdate.requestWindowFeature(Window.FEATURE_NO_TITLE);
                editProfileUpdate.setContentView(R.layout.add_profile_update_dialog);
                TextView gallaryDialog = editProfileUpdate.findViewById(R.id.gallaryDialog);
                TextView cameraDialog = editProfileUpdate.findViewById(R.id.cameraDialog);

                editProfileUpdate.show();
                Window window = editProfileUpdate.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                gallaryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery(PICK_IMAGE);
                        editProfileUpdate.dismiss();
                    }
                });

                cameraDialog.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

/*                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivity(intent,  CAMERA_PIC_REQUEST);

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                        editProfileUpdate.dismiss();*/

                        PackageManager packageManager = StatusListIndivisualDetailsActivity.this.getPackageManager();

                        boolean readExternal = Permission.checkPermissionReadExternal(StatusListIndivisualDetailsActivity.this);
                        boolean writeExternal = Permission.checkPermissionReadExternal2(StatusListIndivisualDetailsActivity.this);
                        boolean camera = Permission.checkPermissionCamera(StatusListIndivisualDetailsActivity.this);

                        if (camera && writeExternal && readExternal) {
                            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {


                                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intentCamera.putExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
                                if (intentCamera.resolveActivity(getPackageManager()) != null) {
                                    try {
                                        pictureFile = createImageFile();

                                        //setUserProfile.setImageBitmap(thumbnail);
                                        Glide.with(StatusListIndivisualDetailsActivity.this)
                                                .load(pictureFile)
                                                .placeholder(R.drawable.ic_launcher_background)
                                                .into(userProfileDetails);
                                        profileImage = pictureFile;


                                        // Toast.makeText(StatusListIndivisualDetailsActivity.this, "Calling inside if", Toast.LENGTH_SHORT).show();
                                        //  profileImage = createImageFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.photo_file_can_t_be_created_please_try_again), Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (pictureFile != null) {
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                                            destinoURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", pictureFile);
                                            intentCamera.setClipData(ClipData.newRawUri("", destinoURI));
                                            intentCamera.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        } else {
                                            destinoURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", pictureFile);
                                        }

                                        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, destinoURI);
                                        startActivityForResult(intentCamera, CAMERA_PIC_REQUEST);
                                        editProfileUpdate.dismiss();
                                    }
                                }

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "camera permission required", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                            editProfileUpdate.dismiss();
                        }

                    }
                });


            }
        });


        editRecordImportDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                intent.putExtra("image_activity", false);
                startActivityForResult(intent, 2);
            }
        });


        editRecordSubmitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameData = nameDetials.getText().toString();
                editMobileData = mobileDetails.getText().toString();
                editPersonIdData = personIdDetails.getText().toString();
                editAboutUsDetailsData = editAboutUsDetails.getText().toString();


               /* editSpinPersonType.getSelectedItem().toString();
                if(spinPersonTypeData.equals("Rent Money"))
                {
                    PersonType = "1";
                }else
                {
                    PersonType = "0";
                }*/

                //API
                edit_record_api();
                record_details_api();
            }
        });


        //geting userID data
        SharedPreferences getUserIdData = getApplication().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        // Taking data pass via intent
        record_id = getIntent().getStringExtra("record_id");
        // Toast.makeText(getApplicationContext(), record_id, Toast.LENGTH_SHORT).show();
        record_details_api();
    }

    private void spinPerson(int pos) {
        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("Loan defaulter");
        groupstatus.add("Bad Tenant");

        spinnerAdapter group_status_adapter = new spinnerAdapter(StatusListIndivisualDetailsActivity.this, R.layout.custom_spinner);
        //group_status_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        group_status_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_edit);
        group_status_adapter.addAll(groupstatus);

        // group_status_adapter.add("Rent Money");
        editSpinPersonType.setAdapter(group_status_adapter);
        editSpinPersonType.setSelection(pos);
        //  Log.e("groupstatus_size" , String.valueOf(groupstatus.size()));

        editSpinPersonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int selectedItemText = editSpinPersonType.getSelectedItemPosition() + 1;
                String status = editSpinPersonType.getSelectedItem().toString();
                if (status.equals("Bad Tenant")) {
                    PersonType = "1";
                } else {
                    PersonType = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void edit_record_api() {

        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), UserID);
        RequestBody EditNameData = RequestBody.create(MediaType.parse("text/plain"), editNameData);
        RequestBody EditMobileData = RequestBody.create(MediaType.parse("text/plain"), editMobileData);
        RequestBody EditPersonIdData = RequestBody.create(MediaType.parse("text/plain"), editPersonIdData);
        RequestBody EditAboutUsDetailsData = RequestBody.create(MediaType.parse("text/plain"), editAboutUsDetailsData);
        RequestBody personType = RequestBody.create(MediaType.parse("text/plain"), PersonType);
        RequestBody Record_id = RequestBody.create(MediaType.parse("text/plain"), record_id);
//        RequestBody IdImage = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idImage));
        MultipartBody.Part profile;
        if (profileImage == null) {
            profile = MultipartBody.Part.createFormData("profile", "", RequestBody.create(MediaType.parse("image/*"), ""));
        } else {
            profile = MultipartBody.Part.createFormData("profile", profileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profileImage));
            Log.e("Photo", String.valueOf(profileImage));
        }


//        if ( Temp_test.isEmpty()) {
//
//
//        } else {
//             helpreportImagesParts = new MultipartBody.Part[Temp_test.size()];
//            for (int index = 0; index < Temp_test.size(); index++) {
////            Log.d("chathelpscreenshots", "requestUploadSurvey: help report image " + index + "  " + Image_list.get(index).getPath());
//
//                try {
//                    File file = new File(Temp_test.get(index).getPath());
//                    String filename = file.getName();
//
//                    RequestBody reportBody = RequestBody.create(MediaType.parse("*/*"), file);
//                    helpreportImagesParts[index] = MultipartBody.Part.createFormData("image[]", file.getName(), reportBody);
//                    Log.v("dahgsdhjgdfhjs", "***********************************************" + filename);
//                } catch (Exception e) {
//                    Log.v("dahgsdhjgdfhjs", "***********************************************" + e);
////                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }


        MultipartBody.Part[] helpreportImagesParts = new MultipartBody.Part[Temp_test.size()];
        for (int index = 0; index < Temp_test.size(); index++) {
            try {
                RequestBody reportBody = RequestBody.create(MediaType.parse("*/*"), Temp_test.get(index));
                helpreportImagesParts[index] = MultipartBody.Part.createFormData("image[]", Temp_test.get(index).getName(), reportBody);
                Log.e("image_path", "Temp_test.get(index)***" + Temp_test.get(index));
            } catch (Exception e) {
                Log.e("image_path_screening", "e3***" + e);
            }
        }

        // show till load api data

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Edit_Record_Model> call = API_Client.getClient().edit_record(authorization,
                Record_id,
                EditNameData,
                EditMobileData,
                EditPersonIdData,
                personType,
                userID,
                EditAboutUsDetailsData,
                profile,
                helpreportImagesParts
        );

        call.enqueue(new Callback<Edit_Record_Model>() {
            @Override
            public void onResponse(Call<Edit_Record_Model> call, Response<Edit_Record_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {
                            Temp_test.clear();
                            Toast.makeText(StatusListIndivisualDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                            //  Toast.makeText(getApplicationContext(), "Clear_Size"+Temp_test.size(), Toast.LENGTH_SHORT).show();
                            record_details_api();

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
            public void onFailure(Call<Edit_Record_Model> call, Throwable t) {
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

    private void record_details_api() {
        // show till load api data
        final ProgressDialog pd = new ProgressDialog(StatusListIndivisualDetailsActivity.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Record_Details_Model> call = API_Client.getClient().r_detail(authorization, record_id, UserID);
        call.enqueue(new Callback<Record_Details_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Record_Details_Model> call, Response<Record_Details_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = String.valueOf(response.body().getSuccess());


                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True")) {

                            Record_Details_Model record_details_model = response.body();
                            Record_Details_Result record_details_result = record_details_model.getData();

                            String userNameData = record_details_result.getName();
                            String userMobileData = String.valueOf(record_details_result.getMobile());
                            String userIdData = record_details_result.getPersonId();
                            String aboutPersonData = record_details_result.getAboutPerson();
                            String imageList = record_details_result.getImage();
                            Integer persontype = record_details_result.getPersonType();
                            Log.e("persontype_data", persontype+"");

                            spinPerson(persontype);

                            if (persontype.equals("0")) {

                                //   int spinnerPosition = group_status_adapter.getPosition("Rent House");
                            //    editSpinPersonType.setSelection(spinnerPosition);
//                                editSpinPersonType.setSelection(getIndex(editSpinPersonType, "Rent House"));
                            } else {
                               // int spinnerPosition = group_status_adapter.getPosition("Rent Money");
                               // editSpinPersonType.setSelection(spinnerPosition);
//                                editSpinPersonType.setSelection(getIndex(editSpinPersonType, "Rent Money"));
                            }


                            //    ArrayList<String[]> arrayList=new ArrayList<>();
                            String[] image_data = imageList.split(",");


                         /*   image_data_file.add(imageList.split(","));


                            Toast.makeText(StatusListIndivisualDetailsActivity.this, image_data_file.size()+"*******&&&&&&&&", Toast.LENGTH_SHORT).show();
                            Log.e("Size_of_list", String.valueOf(image_data_file.size()));*/

                            /*String abc=image_data[0];
                            Toast.makeText(getApplicationContext(),abc,Toast.LENGTH_LONG).show();*/
//                            for (int i=0;i<image_data.length;i++)
//                            {
//
//                               Toast.makeText(getApplicationContext(),image_data,Toast.LENGTH_LONG).show();
//
//                            }
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            recRecordImage.setLayoutManager(linearLayoutManager);
//                            Toast.makeText(getApplicationContext(),arrayList.size()+"ghgh",Toast.LENGTH_LONG).show();
                            Record_Details_Adapter_Image details_adapter_image = new Record_Details_Adapter_Image(StatusListIndivisualDetailsActivity.this, image_data);

                            recRecordImage.setAdapter(details_adapter_image);

//                            for(int i = 0; i <= arrayList.size(); i++)
//                            {
//                                Toast.makeText(getApplicationContext(),arrayList.get(i).toString()+"",Toast.LENGTH_SHORT).show();
//                            }

                            Glide.with(getApplicationContext()).load(API_Client.BASE_IMAGE + record_details_result.getProfile()).into(userProfileDetails);

//                            record_details_result.getImage();
                            nameDetials.setText(userNameData);
                            mobileDetails.setText(userMobileData);
                            personIdDetails.setText(userIdData);
//                            record_details_result.getPersonType();
//                            record_details_result.getProfile();
                            editAboutUsDetails.setText(aboutPersonData);

                        } else {
//                           Toast.makeText(getActivity(), message+success+"BB", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Record_Details_Model> call, Throwable t) {
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

        boolean readExternal = Permission.checkPermissionReadExternal(StatusListIndivisualDetailsActivity.this);
        boolean writeExternal = Permission.checkPermissionReadExternal2(StatusListIndivisualDetailsActivity.this);
        boolean camera = Permission.checkPermissionCamera(StatusListIndivisualDetailsActivity.this);
        if (readExternal && camera) {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, pickImage);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {

            if (resultCode == 0) {
                // taking daya from imagesActivity for add slip record

                Temp_test = ImagesActivity.Temp_file_list;

           /*     selectedImageList = (ArrayList<File>) data.getSerializableExtra("all_file_list");
                selectedImageList3 = (ArrayList<String>)data.getSerializableExtra("selectedImageList");*/

                if (Temp_test.size() == 0) {
                    recRecordImage.setVisibility(View.VISIBLE);
                    recRecordImage2.setVisibility(View.GONE);
                } else {
                    recRecordImage2.setVisibility(View.VISIBLE);
                    recRecordImage.setVisibility(View.GONE);
                }
                //            Toast.makeText(getActivity(), selectedImageList.size()+"", Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getApplicationContext(), Temp_test.size()+"", Toast.LENGTH_SHORT).show();
                Log.e("running_this_function", "*********************running_this_function");
                Log.e("running_this_function", "*********************resultCode" + resultCode);
                Log.e("running_this_function", "*********************RESULT_OK" + RESULT_OK);
//                Add_Doc_Adapter add_doc_adapter = new Add_Doc_Adapter(selectedImageList2, getApplicationContext());
//                selected_recycler_view.setAdapter(add_doc_adapter);
                Edit_Doc_Adapter edit_doc_adapter = new Edit_Doc_Adapter(Temp_test, StatusListIndivisualDetailsActivity.this);
                recRecordImage2.setAdapter(edit_doc_adapter);


                // code for adapter write here
                // make pdf vidibility gone from here

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
            Glide.with(getApplicationContext()).load(selectedImagePath1).into(userProfileDetails);

        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            try {

                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                userProfileDetails.setImageBitmap(mImageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
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
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public class spinnerAdapter extends ArrayAdapter<String> {
        private spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }
        /*@Override
        public int getCount() {
            // TODO Auto-generated method stub
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }*/
    }
}