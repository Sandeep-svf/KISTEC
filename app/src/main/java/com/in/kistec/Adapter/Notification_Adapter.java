package com.in.kistec.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.in.kistec.API_Model.API_Result_Data.Notification_Result;
import com.in.kistec.API_Model.Clear_All_Notification_Model;
import com.in.kistec.API_Model.Clear_Particular_Notification_Model;
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

public class Notification_Adapter  extends RecyclerView.Adapter<NotificationViewHolder> {

    List<Notification_Result> data = new ArrayList<>();
    Context context;
    private  String notifiation_id , UserID , authorization;



    public Notification_Adapter(List<Notification_Result> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notification_view_holder,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.date.setText(data.get(position).getCreatedAt());
        holder.title.setText(data.get(position).getTitle());
        holder.message.setText(data.get(position).getContent());
        notifiation_id = String.valueOf((data.get(position).getId()));

        SharedPreferences getUserIdData = context.getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE);
        UserID = getUserIdData.getString("UserID", "");
        authorization = getUserIdData.getString("authorization", "");

        holder.delete_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifiation_id = String.valueOf(data.get(position).getId());
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.logout_dialogs);
                LinearLayout noDialogLogout = dialog.findViewById(R.id.noDialogLogout);
                LinearLayout yesDialogLogout = dialog.findViewById(R.id.yesDialogLogout);


                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                yesDialogLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_particular_notification(position);
                        dialog.dismiss();
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

    }

    public void removeItem(int position) {
        try {
            data.remove(position);
            notifyDataSetChanged();     // Update data in adapter.... Notify adapter for change data
        } catch (IndexOutOfBoundsException index) {
            index.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete_particular_notification(int position) {
        // api for delete particular notification

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("loading...");
        pd.show();

        // calling API
        Call<Clear_Particular_Notification_Model> call = API_Client.getClient().delete_particular_notification(authorization,UserID , notifiation_id);
        call.enqueue(new Callback<Clear_Particular_Notification_Model>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<Clear_Particular_Notification_Model> call, Response<Clear_Particular_Notification_Model> response) {
                pd.dismiss();


                try {
                    //if api response is successful ,taking message and success
                    if (response.isSuccessful()) {
                        String success = String.valueOf(response.body().getSuccess());
                        String message = String.valueOf(response.body().getMessage());

                        if (success.equals("true") || success.equals("True"))

                        {
                            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                            removeItem(position);

                        }else {

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(context, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            switch (response.code()) {
                                case 400:
                                    Toast.makeText(context, "The server did not understand the request.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 401:
                                    Toast.makeText(context, "Unauthorized The requested page needs a username and a password.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 404:
                                    Toast.makeText(context, "The server can not find the requested page.", Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(context, "Internal Server Error..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 503:
                                    Toast.makeText(context, "Service Unavailable..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 504:
                                    Toast.makeText(context, "Gateway Timeout..", Toast.LENGTH_SHORT).show();
                                    break;
                                case 511:
                                    Toast.makeText(context, "Network Authentication Required ..", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Clear_Particular_Notification_Model> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "This is an actual network failure :( inform the user and possibly retry)", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    Log.e("conversion issue", t.getMessage());
                    Toast.makeText(context, "Please Check your Internet Connection...."+t.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class NotificationViewHolder extends RecyclerView.ViewHolder {
    TextView title, message, date;
    ImageView delete_notification;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        message = itemView.findViewById(R.id.message);
        date = itemView.findViewById(R.id.date);
        delete_notification = itemView.findViewById(R.id.delete_notification);
    }
}