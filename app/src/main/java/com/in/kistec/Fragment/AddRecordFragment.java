package com.in.kistec.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.in.kistec.API_Model.Add_Record_Model;
import com.in.kistec.Activity.Permission;
import com.in.kistec.Adapter.Add_Doc_Adapter;
import com.in.kistec.ImportDocHelperClass.ImagesActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SettingsActivity.MyProfileActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.in.kistec.SettingsActivity.MyProfileActivity.savebitmap;

public class AddRecordFragment extends Fragment {
    CircleImageView addRecordProfile;
    EditText addRecordName, addRecordMobile, addRecordPersonID, addRecordAboutPerson;
    LinearLayout addRecordImportDocument, addRecordSubmitLayout, addRecordPersonProfile;
    Spinner spinPersonType;
    private static final int PICK_IMAGE = 100;
    private static final int READ_REQUEST_CODE = 1;
    File profileImage, documentData;
    String spinPersonTypeData;
    private String PersonType;
    ArrayList<File> imageList1 = new ArrayList<File>();
    ImageView docImage;
    ImageView add_record_background;
    RecyclerView selected_recycler_view;
    private static final int CAMERA_PIC_REQUEST = 3788;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private Uri imageUri5;
    File photo;
    Uri selectedImageUri;

    //............................................


    //...................
    private String UserID, authorization, personTypeData, aboutPersonData, personIdData, mobileData, nameData;

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGES = 2;
    public static final int STORAGE_PERMISSION = 100;


    RecyclerView imageRecyclerView, selectedImageRecyclerView;
    int[] resImg = {R.drawable.ic_camera_white_30dp, R.drawable.ic_folder_white_30dp};
    String[] title = {"Camera", "Folder"};
    String mCurrentPhotoPath;
    String[] projection = {MediaStore.MediaColumns.DATA};
    File image;
    Button done;
    private Object ActivityCompat;
    private ContentValues values5;
    private Bitmap thumbnail5;
    private ArrayList<File> Temp_test = new ArrayList<>();
    private ArrayList<File> Temp_test2 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_record, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        addRecordProfile = v.findViewById(R.id.addRecordProfile);
        addRecordName = v.findViewById(R.id.addRecordName);
        addRecordMobile = v.findViewById(R.id.addRecordMobile);
        addRecordPersonID = v.findViewById(R.id.addRecordPersonID);
        addRecordAboutPerson = v.findViewById(R.id.addRecordAboutPerson);
        addRecordImportDocument = v.findViewById(R.id.addRecordImportDocument);
        addRecordSubmitLayout = v.findViewById(R.id.addRecordSubmitLayout);
        spinPersonType = v.findViewById(R.id.spinPersonType);
        addRecordPersonProfile = v.findViewById(R.id.addRecordPersonProfile);
        docImage = v.findViewById(R.id.docImage);
        selected_recycler_view = v.findViewById(R.id.selected_recycler_view);
        add_record_background = v.findViewById(R.id.add_record_background);
        Glide.with(getActivity()).load(R.drawable.splash_background).into(add_record_background);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        selected_recycler_view.setLayoutManager(linearLayoutManager);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        //geting userID data
        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");


        Log.e("sharedPreferanceSize", "********" + String.valueOf(imageList1.size()));

//        spinPersonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Object item = parent.getItemAtPosition(position);
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        final List<String> groupstatus = new ArrayList<>();
        groupstatus.add("Loan defaulter ");
        groupstatus.add("Bad Tenant");

        spinnerAdapter group_status_adapter = new spinnerAdapter(getActivity(), R.layout.custom_spinner);
        //group_status_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        group_status_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_edit);
        group_status_adapter.addAll(groupstatus);
        group_status_adapter.add("Loan defaulter ");
        spinPersonType.setAdapter(group_status_adapter);
        spinPersonType.setSelection(group_status_adapter.getCount());

        spinPersonType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                int selectedItemText = spinPersonType.getSelectedItemPosition() + 1;
                String status = spinPersonType.getSelectedItem().toString();
                if (status.equals("Loan defaulter ")) {
                    PersonType = "1";
                } else {
                    PersonType = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addRecordPersonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog addRecordPersonProfile = new Dialog(getActivity());
                addRecordPersonProfile.requestWindowFeature(Window.FEATURE_NO_TITLE);
                addRecordPersonProfile.setContentView(R.layout.add_id_update_dialog);
                TextView gallaryDialog = addRecordPersonProfile.findViewById(R.id.gallaryDialog);
                TextView cameraDialog = addRecordPersonProfile.findViewById(R.id.cameraDialog);
                addRecordPersonProfile.show();
                Window window = addRecordPersonProfile.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                gallaryDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openGallery();
                        addRecordPersonProfile.dismiss();
                    }
                });

                cameraDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PackageManager packageManager = getActivity().getPackageManager();

                        boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
                        boolean writeExternal = Permission.checkPermissionReadExternal2(getActivity());
                        boolean camera = Permission.checkPermissionCamera(getActivity());

                        if (camera && writeExternal && readExternal) {
                            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                                values5 = new ContentValues();
                                values5.put(MediaStore.Images.Media.TITLE, "New Picture");
                                values5.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                                imageUri5 = getActivity().getContentResolver().insert(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values5);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri5);
                                startActivityForResult(intent, CAMERA_PIC_REQUEST);
                                addRecordPersonProfile.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), "camera permission required", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                            addRecordPersonProfile.dismiss();
                        }
                    }
                });
                ////////////////////////////////////////////
            }
        });

        addRecordImportDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PackageManager packageManager = getActivity().getPackageManager();

                boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
                boolean writeExternal = Permission.checkPermissionReadExternal2(getActivity());
                if (readExternal || writeExternal) {
                    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {


                        Temp_test = new ArrayList<>();
                        Add_Doc_Adapter add_doc_adapter = new Add_Doc_Adapter(Temp_test2, getActivity());
                        selected_recycler_view.setAdapter(add_doc_adapter);

                        Intent intent = new Intent(getActivity(), ImagesActivity.class);
                        intent.putExtra("image_activity", true);
                        startActivityForResult(intent, 1);
                    }
                } else {
                    Toast.makeText(getActivity(), "storage permission required", Toast.LENGTH_LONG).show();
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

                }
            }
        });

        addRecordSubmitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long fileSizeInBytes = 0;
                spinPersonTypeData = spinPersonType.getSelectedItem().toString();
                for (int i = 0; i < Temp_test.size(); i++) {
                    fileSizeInBytes = fileSizeInBytes + Temp_test.get(i).length();

                }
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                long fileSizeInKB = fileSizeInBytes / 1024;
                //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                long fileSizeInMB = fileSizeInKB / 1024;


                if (addRecordName.getText().toString().trim().equalsIgnoreCase("")) {
                    addRecordName.setError("This field can not be blank");
                    addRecordName.requestFocus();
                    return;
                } else if (addRecordMobile.getText().toString().trim().equalsIgnoreCase("")) {
                    addRecordMobile.setError("This field can not be blank");
                    addRecordMobile.requestFocus();
                    return;
                } else if (addRecordMobile.getText().toString().length() != 10) {
                    Toast.makeText(getActivity(), "Please enter 10 digit valid mobile number", Toast.LENGTH_SHORT).show();

                } else if (addRecordPersonID.getText().toString().trim().equalsIgnoreCase("")) {
                    addRecordPersonID.setError("This field can not be blank");
                    addRecordPersonID.requestFocus();
                    return;
                } else if (addRecordAboutPerson.getText().toString().trim().equalsIgnoreCase("")) {
                    addRecordAboutPerson.setError("This field can not be blank");
                    addRecordAboutPerson.requestFocus();
                    return;
                } else if (profileImage == null) {
                    Toast.makeText(getActivity(), "Please upload profile image", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Temp_test.size() == 0) {
                    Toast.makeText(getActivity(), "Please import document", Toast.LENGTH_SHORT).show();
                    return;
                } else if (fileSizeInMB > 100) {
                    Toast.makeText(getActivity(), "Please import less than 100 MB", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    nameData = addRecordName.getText().toString();
                    mobileData = addRecordMobile.getText().toString();
                    personIdData = addRecordPersonID.getText().toString();
                    aboutPersonData = addRecordAboutPerson.getText().toString();
                    personTypeData = PersonType;

                    //API
                    add_record_api();

                }
            }
        });

        return v;
    }

    public class spinnerAdapter extends ArrayAdapter<String> {
        private spinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            int count = super.getCount();
            return count > 0 ? count - 1 : count;
        }
    }

    private void add_record_api() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        RequestBody Authorization = RequestBody.create(MediaType.parse("text/plain"), authorization);
        RequestBody userID = RequestBody.create(MediaType.parse("text/plain"), UserID);
        RequestBody NameData = RequestBody.create(MediaType.parse("text/plain"), nameData);
        RequestBody MobileData = RequestBody.create(MediaType.parse("text/plain"), mobileData);
        RequestBody PersonIdData = RequestBody.create(MediaType.parse("text/plain"), personIdData);
        RequestBody AboutPersonData = RequestBody.create(MediaType.parse("text/plain"), aboutPersonData);
        RequestBody PersonTypeData = RequestBody.create(MediaType.parse("text/plain"), personTypeData);
//        RequestBody IdImage = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idImage));
        MultipartBody.Part profile = MultipartBody.Part.createFormData("profile", profileImage.getName(), RequestBody.create(MediaType.parse("image/*"), profileImage));


        MultipartBody.Part[] helpreportImagesParts = new MultipartBody.Part[Temp_test2.size()];
        for (int index = 0; index < Temp_test2.size(); index++) {
//            Log.d("chathelpscreenshots", "requestUploadSurvey:selectedImageList2 help report image " + index + "  " + Image_list.get(index).getPath());

            try {

//                String filename=Temp_test.get(index).getName();

                RequestBody reportBody = RequestBody.create(MediaType.parse("*/*"), Temp_test2.get(index));
                helpreportImagesParts[index] = MultipartBody.Part.createFormData("image[]", Temp_test2.get(index).getName(), reportBody);
                Log.e("image_path_screening", "Temp_test2.get(index)***" + Temp_test2.get(index));
            } catch (Exception e) {
                Log.e("image_path_screening", "e3***" + e);
//                Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Add_Record_Model> call = API_Client.getClient().addRecords(authorization,
                userID,
                NameData,
                PersonIdData,
                PersonTypeData,
                AboutPersonData,
                MobileData,
                profile,
                helpreportImagesParts);
        call.enqueue(new Callback<Add_Record_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Add_Record_Model> call, Response<Add_Record_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = String.valueOf(response.body().getSuccess());
                        String message = response.body().getMessage();

                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True")) {
                            Temp_test.clear();

                            Toast.makeText(getActivity(), "Record Added Successfully", Toast.LENGTH_SHORT).show();
                            // onResume();


                            // Clear field after successful Record

                            addRecordName.setText("");
                            addRecordMobile.setText("");
                            addRecordPersonID.setText("");
                            addRecordAboutPerson.setText("");
                            Glide.with(getActivity())
                                    .load(R.drawable.save)
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .into(addRecordProfile);


                            docImage.setVisibility(View.VISIBLE);
                            selected_recycler_view.setVisibility(View.GONE);


                        } else {
                            Toast.makeText(getActivity(), message + " " + success, Toast.LENGTH_LONG).show();
                            pd.dismiss();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(getActivity(), "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(getActivity(), "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(getActivity(), "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(getActivity(), "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(getActivity(), "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(getActivity(), "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(getActivity(), "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getActivity(), "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Log.e("image_path_screening", "e2***" + e);
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    Log.e("image_path_screening", "e***" + e);
                }
            }

            @Override
            public void onFailure(Call<Add_Record_Model> call, Throwable t) {
                Log.e("image_path_screening", "Throwable***" + t);
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("dhdj", t.getMessage());
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);

       /* boolean readExternal = Permission.checkPermissionReadExternal(getActivity());
        boolean writeExternal = Permission.checkPermissionReadExternal2(getActivity());
        boolean camera = Permission.checkPermissionCamera(getActivity());
        if (readExternal && camera) {
           // Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }

        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("image_path_screening", "requestCode***" + requestCode);
        Log.e("image_path_screening", "resultCode***" + resultCode);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            try {
                final Uri imageUri = data.getData();
                // String sel_path = getpath(imageUri);

                try {
                    String path = getPath(getActivity(), data.getData());
                    profileImage = new File(path);
                } catch (URISyntaxException e) {
                }

                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                addRecordProfile.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
            
            /*selectedImageUri = data.getData();
            String sel_path = getpath(selectedImageUri);
            try {

                if (sel_path == null) {
                    Toast.makeText(getActivity(), "Bad image it can not be selected", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("selectedImagePath", sel_path);
                    final File filePath = new File(sel_path);
                    Bitmap bitmap = BitmapFactory.decodeFile(sel_path);
                    File file = savebitmap(bitmap);
                    profileImage = saveBitmapToFile(filePath);

                    Glide.with(getActivity())
                            .asBitmap()
                            .load(filePath)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    addRecordProfile.setImageBitmap(resource);
                                }
                            });
                }
            } catch (Exception e) {

                Toast.makeText(getActivity(), "bad image", Toast.LENGTH_SHORT).show();
            }*/

         /*   String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            profileImage = new File(cursor.getString(column_index));
            Log.e("userImage1", String.valueOf(profileImage));
            String selectedImagePath1 = getPath(selectedImageUri);

            Log.v("Deatils_path", "***" + selectedImagePath1);
            Glide.with(getActivity()).load(selectedImagePath1).into(addRecordProfile);
            Log.e("userImage1", "BB");*/
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK || resultCode == 0) {
                docImage.setVisibility(View.GONE);
                selected_recycler_view.setVisibility(View.VISIBLE);
                /*Temp_test = (ArrayList<File>)data.getSerializableExtra("Temp_file_list");
                selectedImageList = (ArrayList<File>)data.getSerializableExtra("all_file_list");
                 selectedImageList2 = (ArrayList<String>)data.getSerializableExtra("selectedImageList");*/

                Temp_test = ImagesActivity.Temp_file_list;
                Log.e("image_path_screening", "pdf_show***" + Temp_test.size());

                for (int i = 0; i < Temp_test.size(); i++) {
                    Temp_test2.add(Temp_test.get(i));
                    //Sending_list.add(getImagePath(Uri.fromFile(Temp_test.get(i))));
                    Log.e("image_path_screening", "******" + Temp_test2.get(i));
                }


                if (Temp_test.size() == 0) {
                    docImage.setVisibility(View.VISIBLE);
                    selected_recycler_view.setVisibility(View.GONE);
                } else {
                    docImage.setVisibility(View.GONE);
                    selected_recycler_view.setVisibility(View.VISIBLE);
                }

                Add_Doc_Adapter add_doc_adapter = new Add_Doc_Adapter(Temp_test2, getActivity());
                selected_recycler_view.setAdapter(add_doc_adapter);

            }
//        } else if (resultCode == RESULT_OK && requestCode == 8255415) {
//            Toast.makeText(getActivity(),"********** Entry point   ********************",Toast.LENGTH_SHORT).show();
//            try {
//                Log.e("userImage1", "121212");
//                ClipData selectedImageUri = data.getClipData();
//
//                ArrayList<String> mArrayUri = new ArrayList<String>();
//                selectedImageList2 = (ArrayList<String>)data.getSerializableExtra("selectedImageList");
//
//                for(int i = 0; i < selectedImageList2.size(); i++)
//                {
//                    File temp = new File(selectedImageList2.get(i));
//                    Toast.makeText(getActivity(),temp.getParent(),Toast.LENGTH_SHORT).show();
//                    Log.e("hfsdjkfhjs","##################333"+ String.valueOf(temp));
//
//                }
//
//
//
//            } catch (Exception e) {
//                Log.e("userImage1", e.getLocalizedMessage()+"........");
//
//            }
            /*String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();*/
            //documentData = new File(cursor.getString(column_index));
//            imageList.add(documentData);
            //Log.e("documentData", String.valueOf(docImage));

//            Toast.makeText(getActivity(),imageList.size(),Toast.LENGTH_SHORT).show();
            /*Uri selectedImageUri = data.getData();
            String selectedImagePath = getPath2(selectedImageUri);
            Log.e("userImage1", selectedImagePath+"A");
            Glide.with(getActivity()).load(selectedImagePath).into(docImage);*/
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {

            try {
                thumbnail5 = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), imageUri5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //setUserProfile.setImageBitmap(thumbnail);


            File file = new File(getRealPathFromURIs(imageUri5));

            Glide.with(getActivity())
                    .load(file)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(addRecordProfile);

            // Glide.with(getActivity()).load(thumbnail5).into(addRecordProfile);
            // Picasso.get().load(String.valueOf(thumbnail)).into(setUserProfile);
            // compress file before send to server
            //  Picasso.get().load(new File(selectedImagePath9)).resize(1000,1000).into((Target) profileImage);
            // Getting Real uri path
            profileImage = new File(getRealPathFromURIs(imageUri5));
        }
    }


    // *********************************************** METHOD *******************************************************************


    public String getRealPathFromURIs(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);


    }

    private void call(String selectedImagePath1, int ii) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(getActivity()).load(selectedImagePath1).into(docImage);

            }
        }, ii);
    }

    private String getPath2(Uri selectedImageUri) {
        // just some safety built in
        if (selectedImageUri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return selectedImageUri.getPath();
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
            Toast.makeText(getActivity(), "*********File did not compressed************", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
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

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{split[1]};
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /////////////////////////////////

    public String getImagePath(Uri uri) {
        String path = "";
        try {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getActivity().getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
        }

        return path;
    }


    @Override
    public void onResume() {

        super.onResume();
        this.onCreate(null);

    }

}

