package com.in.kistec.ImportDocHelperClass;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.kistec.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ImagesActivity extends FragmentActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGES = 2;
    public static final int STORAGE_PERMISSION = 100;

    public static ArrayList<ImageModel> imageList;
    public static  ArrayList<String> selectedImageList=new ArrayList<>();
    public static  ArrayList<String> selectedImageList2=new ArrayList<>();
    public static  ArrayList<File> all_file_list=new ArrayList<>();
    public static  ArrayList<File> Temp_file_list=new ArrayList<>();

    RecyclerView imageRecyclerView, selectedImageRecyclerView;
    int[] resImg = {R.drawable.ic_camera_white_30dp, R.drawable.ic_folder_white_30dp};
    String[] title = {"Camera", "Folder"};
    String mCurrentPhotoPath;
    SelectedImageAdapter selectedImageAdapter;
    ImageAdapter imageAdapter;
    String[] projection = {MediaStore.MediaColumns.DATA};
    File image;
    Button done;
    private Object ActivityCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

//        if (isStoragePermissionGranted()) {
                init();
            getAllImages();
            setImageList();
            setSelectedImageList();

        }
  //  }

    public void init() {
        imageRecyclerView = findViewById(R.id.recycler_view);
        selectedImageRecyclerView = findViewById(R.id.selected_recycler_view);
        done = findViewById(R.id.done);
        imageList = new ArrayList<>();
        selectedImageList = new ArrayList<>();
        selectedImageList2 = new ArrayList<>();
        all_file_list = new ArrayList<>();
        Temp_file_list = new ArrayList<>();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < selectedImageList.size(); i++) {

/*
                    all_file_list.add(new File(String.valueOf(selectedImageList)));
                    Toast.makeText(getApplicationContext(), all_file_list.size(), Toast.LENGTH_LONG).show();
                    all_file_list.add(new File(String.valueOf(selectedImageList)));
*/
                    all_file_list.add(new File((selectedImageList.get(i))));

                    Log.e("image_path_screening","done***"+selectedImageList.get(i));
                }

//                    Intent intent = new Intent(getApplicationContext(), AddRecordFragment.class);
//                    intent.putExtra("selectedImageList", selectedImageList);
//                    startActivity(intent);

                /*intent.putExtra("all_file_list", all_file_list);
                intent.putExtra("selectedImageList", selectedImageList);
                intent.putExtra("Temp_file_list", Temp_file_list);*/
                //setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    public void setImageList() {
        imageRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        imageAdapter = new ImageAdapter(getApplicationContext(), imageList);
        imageRecyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (position == 0) {
                    takePicture();
                } else if (position == 1) {
                    getPickImageIntent();
                } else {
                    try {
                        if (!imageList.get(position).isSelected()) {
                            selectImage(position);
                        } else {
                            unSelectImage(position);
                        }
                    } catch (ArrayIndexOutOfBoundsException ed) {
                        ed.printStackTrace();
                    }
                }
            }
        });
        setImagePickerList();
    }

    public void setSelectedImageList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        selectedImageRecyclerView.setLayoutManager(layoutManager);
        selectedImageAdapter = new SelectedImageAdapter(this, selectedImageList);
        selectedImageRecyclerView.setAdapter(selectedImageAdapter);
    }

    // Add Camera and Folder in ArrayList
    public void setImagePickerList() {
        for (int i = 0; i < resImg.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setResImg(resImg[i]);
            imageModel.setTitle(title[i]);
            imageList.add(i, imageModel);
//            all_file_list.add(new File( ""));

        }
        imageAdapter.notifyDataSetChanged();
    }

    // get all images from external storage
    public void getAllImages() {
        imageList.clear();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ImageModel ImageModel = new ImageModel();
            ImageModel.setImage(absolutePathOfImage);
            imageList.add(ImageModel);
        }
        cursor.close();
    }

    // start the image capture Intent
    public void takePicture() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Continue only if the File was successfully created;
        File photoFile = createImageFile();
        Temp_file_list.add(photoFile);
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getPickImageIntent() {

//        String[] mimeTypes = {"image/*", "application/pdf"};
//
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*|application/pdf")
//                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
//        File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File imageFile = new File(dirPath, "YourPicture.jpg");
//
//        try {
//            if(!dirPath.isDirectory()) {
//                dirPath.mkdirs();
//            }
//            imageFile.createNewFile();
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            startActivityForResult(intent, PICK_IMAGES);
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }


        String[] mimeTypes = {"image/*", "application/pdf"};

    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT).setType("*/*")
            .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES);

    }

    // Add image in SelectedArrayList
    public void selectImage(int position) {
        // Check before add new item in ArrayList;
        if (!selectedImageList.contains(imageList.get(position).getImage())) {
            imageList.get(position).setSelected(true);
            selectedImageList.add(0, imageList.get(position).getImage());
            Temp_file_list.add(0, new File(imageList.get(position).getImage()));
            selectedImageAdapter.notifyDataSetChanged();
            imageAdapter.notifyDataSetChanged();
        }
    }

    // Remove image from selectedImageList
    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (imageList.get(position).getImage() != null) {
                if (selectedImageList.get(i).equals(imageList.get(position).getImage())) {
                    imageList.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    Temp_file_list.remove(i);
                    selectedImageAdapter.notifyDataSetChanged();
                    imageAdapter.notifyDataSetChanged();
                }
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    addImage(mCurrentPhotoPath);
                    all_file_list.add(new File( mCurrentPhotoPath));

                    setSelectedImageList();
                }
            } else if (requestCode == PICK_IMAGES) {
                try {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            //all_file_list.add(new File((uri.getPath())));
                            String path = FileUtils.getDriveFile(this, uri);

                            //String imageFileName = "IMG_" + Calendar.getInstance().getTimeInMillis() + path.substring(path.lastIndexOf("."));
                          //  File pictureFile = new File(externalStorageDirectory(ImagesActivity.this).getPath(), imageFileName);
                            File pictureFile = new File(path);

                            Log.e("image_path_screening","path**"+path);
                            Temp_file_list.add(pictureFile);
                            all_file_list.add(pictureFile);
                            selectedImageList.add(path);
                            setSelectedImageList();
                            //getImageFilePath(uri);
//                       all_file_list.add(new File(String.valueOf(item.getUri())));
//                        Toast.makeText(this, all_file_list.size(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (data.getData() != null) {
                        Uri uri = data.getData();
                        String path = FileUtils.getDriveFile(this, uri);
                        Log.e("image_path_screening","pathpictureFile**"+path);
                        String imageFileName = "IMG_" + Calendar.getInstance().getTimeInMillis() + path.substring(path.lastIndexOf("."));
                        File pictureFile = new File(externalStorageDirectory(ImagesActivity.this).getPath(), imageFileName);
                       File old = new File(path);
                        try {
                            pictureFile.createNewFile();

                            try {

                                //create output directory if it doesn't exist
                               /* File dir = new File (pictureFile.getPath());
                                if (!dir.exists())
                                {
                                    dir.mkdirs();
                                }*/


                                FileInputStream in = new FileInputStream(old);
                                FileOutputStream out = new FileOutputStream(pictureFile);

                                byte[] buffer = new byte[10000];
                                int read;
                                while ((read = in.read(buffer)) != -1) {
                                    out.write(buffer, 0, read);
                                }
                                in.close();
                                in = null;

                                // write the output file (You have now copied the file)
                                out.flush();
                                out.close();
                                out = null;
                                all_file_list.add(pictureFile);
                                Temp_file_list.add(pictureFile);
                                selectedImageList.add(path);
                                setSelectedImageList();

                            }  catch (FileNotFoundException fnfe1) {
                                Log.e("image_path_screening","fnfe1**"+fnfe1);
                            }
                            catch (Exception e) {
                                Log.e("image_path_screening","4444**"+e);
                            }

                        } catch (Exception e) {
                            Log.e("image_path_screening","ExceptioncreateNewFile**"+e);
                        }
//                        String path = getPath( uri);

                        //getImageFilePath(uri);
//
                    }
                } catch (Exception e) {
                    Log.e("image_path_screening","Exception**"+e);
                }
            }
            }
    }
    private static File externalStorageDirectory(FragmentActivity context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + "Items");
    }
    // Get image file path
    public void getImageFilePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (absolutePathOfImage != null) {
                    checkImage(absolutePathOfImage);
                } else {
                    checkImage(String.valueOf(uri));
                }
            }
        }
    }

    // add image in selectedImageList and imageList
    public void checkImage(String filePath) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            for (int pos = 0; pos < imageList.size(); pos++) {
                if (imageList.get(pos).getImage() != null) {
                    if (imageList.get(pos).getImage().equalsIgnoreCase(filePath)) {
                        imageList.remove(pos);
                    }
                }
            }
            addImage(filePath);
//            all_file_list.add(new File(filePath));
        }
    }

    // add image in selectedImageList and imageList
    public void addImage(String filePath) {
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(filePath);
        imageModel.setSelected(true);
        imageList.add(2, imageModel);
        selectedImageList.add(0, filePath);
//        all_file_list.add(new File(filePath));
        selectedImageAdapter.notifyDataSetChanged();
        imageAdapter.notifyDataSetChanged();
    }

//    public boolean isStoragePermissionGranted() {
//        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
//            return false;
//        }
//        return true;
//    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            getAllImages();
            setImageList();
        }
    }


    public String getPath(Uri uri)
    {
        Cursor cursor=null;
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
}
