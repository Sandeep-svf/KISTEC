package com.in.kistec.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.All_Record_Result;
import com.in.kistec.API_Model.API_Result_Data.All_Search_List_Model_Response;
import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;
import com.in.kistec.API_Model.All_Record_Model;
import com.in.kistec.API_Model.All_Search_List_Model;
import com.in.kistec.API_Model.Status_List_Model;
import com.in.kistec.Adapter.SearchListAdapter;
import com.in.kistec.Adapter.StatusListAdapter;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class HomeFragment extends Fragment {
    LinearLayout searchAllRecord, search_badge, rcv_search_list;
    FrameLayout background_search_image;
    EditText getSearchData;
    private  String searchData, UserID,authorization , listSize ;
    TextView personName, personContact, aboutPerson, personiD , nomberOfResult, justifyTxt;
    CircleImageView personProfile;
    RelativeLayout noResultLayout;
    ImageView image_search_wallpaper ;

    RecyclerView rcvSearchList;
    SearchListAdapter searchListAdapter;
    List<All_Search_List_Model_Response> searchListData=new ArrayList<>();


    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);

        searchAllRecord = v.findViewById(R.id.searchAllRecord);
        getSearchData = v.findViewById(R.id.getSearchData);
        personName = v.findViewById(R.id.personName);
        personContact = v.findViewById(R.id.personContact);
        aboutPerson = v.findViewById(R.id.aboutPerson);
        aboutPerson = v.findViewById(R.id.aboutPerson);
        personiD = v.findViewById(R.id.personiD);
        personProfile = v.findViewById(R.id.personProfile);
        search_badge = v.findViewById(R.id.search_badge);
        rcvSearchList = v.findViewById(R.id.rcvSearchList);
        nomberOfResult = v.findViewById(R.id.nomberOfResult);
        noResultLayout = v.findViewById(R.id.noResultLayout);
        rcv_search_list = v.findViewById(R.id.rcv_search_list);
        justifyTxt = v.findViewById(R.id.justifyTxt);
        background_search_image = v.findViewById(R.id.background_search_image);


        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvSearchList.setLayoutManager(linearLayoutManager);

        /*statusListAdapter = new SearchListAdapter(getActivity());
        rcvSearchList.setAdapter(statusListAdapter);*/

        noResultLayout.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            justifyTxt.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        searchAllRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSearchData.getText().toString().trim().equalsIgnoreCase("")) {
                    getSearchData.setError("This field can not be blank");
                    getSearchData.setTextColor(Color.BLACK);
                    getSearchData.requestFocus();
                    return;
                }

                searchData = getSearchData.getText().toString();

                SharedPreferences getSearchData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = getSearchData.edit();
                editor.putString("searchData", String.valueOf(searchData));
                Log.e("record_id" ,"record_id is: " +searchData);
                editor.apply();                //API
                search_all_record_api();

            }
        });

            return  v;
    }

    private void search_all_record_api() {

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<All_Search_List_Model> call = API_Client.getClient().all_records(authorization,UserID,searchData);
        call.enqueue(new Callback<All_Search_List_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<All_Search_List_Model> call, Response<All_Search_List_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = response.body().getSuccess();
                        String message = response.body().getMessage();

                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True"))

                        {
                            background_search_image.setVisibility(View.GONE);
                            rcv_search_list.setVisibility(View.VISIBLE);
                            noResultLayout.setVisibility(View.VISIBLE);
                           // List<All_Search_List_Model_Response> all_search_list_model_responses = (List<All_Search_List_Model_Response>) response.body();
                            searchListData = response.body().getData();
                            listSize = String.valueOf(searchListData.size());
                            nomberOfResult.setText(listSize);
                            Log.e("listSize", "listSize is: " +listSize);

                            searchListAdapter = new SearchListAdapter( searchListData,getActivity());
                            rcvSearchList.setAdapter(searchListAdapter);

                        }else {
                            background_search_image.setVisibility(View.VISIBLE);
                            rcv_search_list.setVisibility(View.GONE);
                            noResultLayout.setVisibility(View.GONE);
//                            Toast.makeText(getActivity(), message+success+"BB", Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                          /*  search_badge.setVisibility(View.GONE);
                            image_search_wallpaper.setVisibility(View.VISIBLE);*/

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
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<All_Search_List_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }
}
