package com.in.kistec.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.in.kistec.API_Model.API_Result_Data.Notification_Result;
import com.in.kistec.API_Model.Clear_All_Notification_Model;
import com.in.kistec.API_Model.Notification_Model;

import com.in.kistec.Adapter.Notification_Adapter;

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

public class NotificatonFragment extends Fragment {

    RecyclerView rcvNotification;
    private  String UserID , authorization;
    Notification_Adapter notofication_adapter;
    View clear_all_layout;

    List<Notification_Result> list = new ArrayList<>();
    CardView clear_all_tab_api;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificatonFragment() {
        // Required empty public constructor
    }


    public static NotificatonFragment newInstance(String param1, String param2) {
        NotificatonFragment fragment = new NotificatonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blank,container,false);
        rcvNotification = v.findViewById(R.id.rcvNotification);
        clear_all_tab_api = v.findViewById(R.id.clear_all_tab_api);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        rcvNotification.setLayoutManager(linearLayoutManager2);

        clear_all_tab_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               final Dialog clear_all_dialog = new Dialog(getActivity());
                clear_all_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                clear_all_dialog.setContentView(R.layout.clear_all_dialog);
                LinearLayout noDialogLogout = clear_all_dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = clear_all_dialog.findViewById(R.id.yesDialogLogout);


                clear_all_dialog.show();
                Window window = clear_all_dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        clear_all_notification_api();
                        notification_api();

                        clear_all_dialog.dismiss();


                    }
                });

                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clear_all_dialog.dismiss();

                    }
                });


            }
        });

        //geting userID data
        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        notification_api();



//        return inflater.inflate(R.layout.fragment_blank, container, false);
        return  v;
    }

    private void clear_all_notification_api() {


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Clear_All_Notification_Model> call = API_Client.getClient().delete_all_notification(authorization,UserID);
        call.enqueue(new Callback<Clear_All_Notification_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Clear_All_Notification_Model> call, Response<Clear_All_Notification_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = String.valueOf(response.body().getSuccess());
                        String message = String.valueOf(response.body().getMessage());

                        if (success.equals("true") || success.equals("True"))

                        {
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();


                        }else {

                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();


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
            public void onFailure(Call<Clear_All_Notification_Model> call, Throwable t) {
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


    private void notification_api() {
        // show till load api data


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        Call<Notification_Model> call = API_Client.getClient().get_notfication(authorization, UserID);

        call.enqueue(new Callback<Notification_Model>() {
            @Override
            public void onResponse(Call<Notification_Model> call, Response<Notification_Model> response) {
                pd.dismiss();
                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());

                        if (success.equals("true") || success.equals("True")) {

                            list = response.body().getData();

                            if(list.size() == 0){
                                clear_all_tab_api.setVisibility(View.GONE);
                            }else {
                                clear_all_tab_api.setVisibility(View.VISIBLE);
                            }

                            Log.e("list_size", ": AAAAAAAAAA" +list.size());

                            /*statusListAdapter = new StatusListAdapter( list,getActivity());
                            recSubmitStatus.setAdapter(statusListAdapter);
*/
                            notofication_adapter = new Notification_Adapter(list,getActivity());
                            rcvNotification.setAdapter(notofication_adapter);


                        } else {
                            Toast.makeText(getActivity(), message + "\n" + success + "\n" + "Please Try Again", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Notification_Model> call, Throwable t) {
                Log.e("conversion issue", t.getMessage());

                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(getActivity(), "Please Check your Internet Connection...." + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}