package com.in.kistec.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;
import com.in.kistec.API_Model.Status_List_Model;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.StatusListIndivisualDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StatusListAdapter extends RecyclerView.Adapter<StatusListViewHolder> {

    private String status = null , apiStatus, record_id;
    private  final String inProcess = "1";
    ArrayList<Status_List_Result> data;
    Context context;

    public StatusListAdapter(List<Status_List_Result> data, Context context) {
        this.data = (ArrayList<Status_List_Result>) data;
        this.context = context;
    }

    @NonNull
    @Override
    public StatusListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.submit_status_view_holder,parent,false);
        return new StatusListViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull StatusListViewHolder holder, int position) {
        holder.personNameStatus.setText(data.get(position).getName());
        holder.mobileNumerStatus.setText(String.valueOf((data.get(position).getMobile())));
//        holder.status.setText(String.valueOf(data.get(position).getStatus()));
        apiStatus = String.valueOf(data.get(position).getStatus());
        Glide.with(context).load(API_Client.BASE_IMAGE+data.get(position).getProfile()).into(holder.persomProfileStatus);

        Log.e("api_status_value","**********"+apiStatus);
      /*  0= PROCESS
        1 =- ACTIVE
        2 = BLOCK*/

        if(  apiStatus.contains("0"))
        {
            status = "In Process";

        }else if( apiStatus.contains("1"))
        {
            status = "Approved";

        }else if(apiStatus.contains("2"))
        {
            status = "Blocked";

        }

        holder.status1.setText(status);


        holder.statusListViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String apiStatuss = String.valueOf(data.get(position).getStatus());
                if( apiStatuss.contains("0"))
                {
                    record_id= String.valueOf(data.get(position).getId());
                    Log.e("record_id" , record_id);
                    Intent intent = new Intent(context, StatusListIndivisualDetailsActivity.class);
                    intent.putExtra("record_id",record_id);
                    context.startActivity(intent);
                }else if ( apiStatuss.contains("2"))
                {
                    Toast.makeText(context, "Your record is Blocked", Toast.LENGTH_SHORT).show();
                    // r YOUR RECORD IS BLOCKED
                }else if (apiStatus.contains("1"))
                {
                    Toast.makeText(context, "Your record is Approved", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class StatusListViewHolder extends RecyclerView.ViewHolder {
    CircleImageView persomProfileStatus;
    TextView personNameStatus, status1, mobileNumerStatus;
    LinearLayout statusListViewHolder;
    public StatusListViewHolder(@NonNull View itemView) {
        super(itemView);
        persomProfileStatus = itemView.findViewById(R.id.persomProfileStatus);
        personNameStatus = itemView.findViewById(R.id.personNameStatus);
        mobileNumerStatus = itemView.findViewById(R.id.mobileNumerStatus);
        status1 = itemView.findViewById(R.id.status);
        statusListViewHolder = itemView.findViewById(R.id.statusListViewHolder);
    }
}
