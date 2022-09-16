package com.in.kistec.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.in.kistec.API_Model.Logout_Model;
import com.in.kistec.API_Model.Status_List_Model;
import com.in.kistec.Adapter.StatusListAdapter;
import com.in.kistec.LoginActivity;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.SettingsActivity.AboutUsActivity;
import com.in.kistec.SettingsActivity.ChangePasswordActivity;
import com.in.kistec.SettingsActivity.ContactUsActivity;
import com.in.kistec.SettingsActivity.MyProfileActivity;
import com.in.kistec.SettingsActivity.PrivacyPolicyActivity;
import com.in.kistec.SettingsActivity.TermAndConditionActivity;
import com.in.kistec.SettingsActivity.UpdateEmailActivity;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ManageProfileFragment extends Fragment {
    RelativeLayout myProfileLayout, changePasswordLayout, privacyPolicyLayout , termAndConditionLayout, logoutLayout,
            aboutUsLayout, contactUsLayout;

     String UserID, authorization;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_manage_profile,container,false);

        myProfileLayout = v.findViewById(R.id.myProfileLayout);
        changePasswordLayout = v.findViewById(R.id.changePasswordLayout);
//        privacyPolicyLayout = v.findViewById(R.id.privacyPolicyLayout);
        termAndConditionLayout = v.findViewById(R.id.termAndConditionLayout);
        logoutLayout = v.findViewById(R.id.logoutLayout);
        aboutUsLayout = v.findViewById(R.id.aboutUsLayout);
        contactUsLayout = v.findViewById(R.id.contactUsLayout);
        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        Log.e("user_id", String.valueOf(UserID));
        Log.e("user_id", String.valueOf(authorization));
        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);  
            }
        });

        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);

            }
        });


        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.logout_dialog);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //geting userID data


                        SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = getUserIdData.edit();
                        editor.putString("UserID", "");
                        editor.putString("authorization", "");

                        editor.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("finish", true);
                        startActivity(intent);

//                        logout_api();
                    }

                });

                noDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

            }
        });

        termAndConditionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TermAndConditionActivity.class);
                startActivity(intent);

            }
        });


//        privacyPolicyLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PrivacyPolicyActivity.class);
//                startActivity(intent);
//
//            }
//        });

        changePasswordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);

            }
        });


        myProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });


        return  v;
    }

    private void logout_api() {
        // show till load api data
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Logout_Model> call = API_Client.getClient().logout(authorization,UserID);
        call.enqueue(new Callback<Logout_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Logout_Model> call, Response<Logout_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String   message = response.body().getMessage();
                        String success = String.valueOf(response.body().getSuccess());


                        // if sucess is true , take all data in to list and set adapter
                        if (success.equals("true") || success.equals("True"))

                        {
                            Toast.makeText(getActivity(),success+message,Toast.LENGTH_SHORT).show();



                            SharedPreferences getUserIdData = getActivity().getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = getUserIdData.edit();
                            editor.putString("UserID", "");
                            editor.putString("authorization", "");

                            editor.apply();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("finish", true);
                            startActivity(intent);

                        }else {
//                            Toast.makeText(getActivity(), message+success+"BB", Toast.LENGTH_LONG).show();
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
            public void onFailure(Call<Logout_Model> call, Throwable t) {
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
