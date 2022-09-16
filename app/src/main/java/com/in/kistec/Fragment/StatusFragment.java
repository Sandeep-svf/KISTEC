package com.in.kistec.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;
import com.in.kistec.API_Model.Status_List_Model;
import com.in.kistec.Adapter.StatusListAdapter;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class StatusFragment extends Fragment {

    RecyclerView recSubmitStatus;
    StatusListAdapter statusListAdapter;
    List<Status_List_Result> list=new ArrayList<>();
     private String UserID, authorization;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_status,container,false);

        recSubmitStatus = v.findViewById(R.id.recSubmitStatus);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);


//        recordStatusAdapter = new RecordStatusAdapter(dataqueue(), getActivity());
//        recSubmitStatus.setAdapter(recordStatusAdapter);
        recSubmitStatus.setLayoutManager(linearLayoutManager2);

        //geting userID data
        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        //API
        record_status_api();

        return  v;
    }

    private void record_status_api() {

        // show till load api data
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Status_List_Model> call = API_Client.getClient().user_record_list(authorization,UserID);
        call.enqueue(new Callback<Status_List_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Status_List_Model> call, Response<Status_List_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = response.body().getSuccess();


                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True"))

                        {
                            list=response.body().getData();
                            statusListAdapter = new StatusListAdapter( list,getActivity());
                            recSubmitStatus.setAdapter(statusListAdapter);



                        }else {
                           // Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Status_List_Model> call, Throwable t) {
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

//    private ArrayList<PersonStatusModel> dataqueue() {
//        ArrayList<PersonStatusModel> holder = new ArrayList<PersonStatusModel>();
//        PersonStatusModel personStatusModel = new PersonStatusModel();
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile1);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile2);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile3);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile5);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile6);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile1);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile2);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("Pending");
//        holder.add(personStatusModel);
//
//        personStatusModel = new PersonStatusModel();
//        personStatusModel.setPersonProfileStatus(R.drawable.profile3);
//        personStatusModel.setPersonNameStatus("Leion Wilson");
//        personStatusModel.setMobileNumberStatus("91256475XX");
//        personStatusModel.setStatus("In Process");
//        holder.add(personStatusModel);
//
//
//        return holder;
//    }


    @Override
    public void onResume() {
        super.onResume();
        record_status_api();
    }
}
