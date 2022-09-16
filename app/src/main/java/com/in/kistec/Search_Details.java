package com.in.kistec;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.User_Detials_Result;
import com.in.kistec.API_Model.All_Search_List_Model;
import com.in.kistec.API_Model.Data;
import com.in.kistec.API_Model.Record_Details_Search_Model;
import com.in.kistec.API_Model.User_Details_Model;
import com.in.kistec.Adapter.SearchListAdapter;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.User.splashActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search_Details extends AppCompatActivity {
    private String record_id , UserID , authorization , searchData;
    CircleImageView personProfile;
    TextView personName , personContact , personiD , aboutPerson;
    private ProgressDialog pd;
    ImageView search_details_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__details);
        personProfile = findViewById(R.id.personProfile);
        personName = findViewById(R.id.personName);
        personContact = findViewById(R.id.personContact);
        personiD = findViewById(R.id.personiD);
        aboutPerson = findViewById(R.id.aboutPerson);
        search_details_background = findViewById(R.id.search_details_background);

        Glide.with(Search_Details.this).load(R.drawable.splash_background).into(search_details_background);

        SharedPreferences getUserIdData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");
        record_id = getIntent().getStringExtra("record_id");
        SharedPreferences getSearchData = getApplicationContext().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        searchData = getUserIdData.getString("searchData", "");


        Log.e("record_id" ,"record_id is: " +record_id);
        Log.e("record_id" ,"record_id is: " +searchData);
        Log.e("record_id" ,"record_id is: " +authorization);
        Log.e("record_id" ,"record_id is: " +UserID);

        record_details_api();
    }

    private void record_details_api() {

        // show till load api data
        pd = new ProgressDialog(Search_Details.this);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();
        

        // calling API
        Call<Record_Details_Search_Model> call = API_Client.getClient().record_details(authorization,UserID,record_id,searchData);
        call.enqueue(new Callback<Record_Details_Search_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Record_Details_Search_Model> call, Response<Record_Details_Search_Model> response) {
                pd.dismiss();
                Log.e("record_details" , "Inside API");

                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();


                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True"))

                        {
                          //  Toast.makeText(Search_Details.this, message, Toast.LENGTH_SHORT).show();
                            Record_Details_Search_Model record_details_search_model = response.body();
                            Data data = record_details_search_model.getData();

                                String personNameData = data.getName();
                                String personMobileData = data.getMobile();
                                String personAboutData = data.getAboutPerson();
                                String personIdData = String.valueOf(data.getPersonId());
                                Glide.with(getApplicationContext()).load(API_Client.BASE_IMAGE+data.getProfile()).into(personProfile);

                                personContact.setText(personMobileData);
                                personiD.setText(personIdData);
                                aboutPerson.setText(personAboutData);
                                personName.setText(personNameData);

                                Log.e("record_details" , "After API" +personNameData);
                                Log.e("record_details" , "After API" +personMobileData);



                           /* for(int i=0;i<all_search_list_model_responses.size();i++)
                            {
                               String peronNameData =  all_search_list_model_responses.get(i).getName();
                            }*/
                         /*   Toast.makeText(getActivity(),success,Toast.LENGTH_SHORT).show();
                            List<All_Record_Result> all_record_results =response.body().getData();
                            for(int i=0;i<all_record_results.size();i++)
                            {
                                String personNameData=all_record_results.get(i).getName();
                                String personMobileData  = String.valueOf(all_record_results.get(i).getMobile());
                                String personIdData  = String.valueOf(all_record_results.get(i).getPersonId());
                                String personAboutData  = String.valueOf(all_record_results.get(i).getAboutPerson());
                                String image=all_record_results.get(i).getProfile();

                               personName.setText(personNameData);
                               personContact.setText(personMobileData);
                                personiD.setText(personIdData);
                               aboutPerson.setText(personAboutData);

                                Glide.with(getActivity()).load(API_Client.BASE_IMAGE+image).into(personProfile);

                                search_badge.setVisibility(View.VISIBLE);
                                image_search_wallpaper.setVisibility(View.GONE);

                            }*/

                        }else {
//                            Toast.makeText(getActivity(), message+success+"BB", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                          /*  search_badge.setVisibility(View.GONE);
                            image_search_wallpaper.setVisibility(View.VISIBLE);*/

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
            public void onFailure(Call<Record_Details_Search_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
}