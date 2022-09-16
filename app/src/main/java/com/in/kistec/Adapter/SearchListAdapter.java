package com.in.kistec.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.in.kistec.API_Model.API_Result_Data.All_Search_List_Model_Response;
import com.in.kistec.API_Model.API_Result_Data.Status_List_Result;
import com.in.kistec.R;
import com.in.kistec.Retrofit.API_Client;
import com.in.kistec.Search_Details;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListViewHolder> {

Context context;
    ArrayList<All_Search_List_Model_Response> data;
    public SearchListAdapter(List<All_Search_List_Model_Response> data , Context context) {
        this.context = context;
        this.data = (ArrayList<All_Search_List_Model_Response>) data;
    }

    @NonNull
    @Override
    public SearchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.search_list_view_holder,parent,false);
        return new SearchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListViewHolder holder, int position) {

        holder.name.setText(data.get(position).getPersonName());
        holder.reportedByName.setText(data.get(position).getName());
        holder.mobile.setText(data.get(position).getMobile());
        holder.nationalId.setText(data.get(position).getPersonId());
        Glide.with(context).load(API_Client.BASE_IMAGE+data.get(position).getProfile()).into(holder.image);


        holder.searchListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record_id = String.valueOf(data.get(position).getRecordId());
                Intent intent = new Intent(context, Search_Details.class);
                intent.putExtra("record_id", record_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class SearchListViewHolder extends RecyclerView.ViewHolder {
    CircleImageView image;
    TextView name, nationalId , mobile , reportedByName ;
    LinearLayout searchListLayout;

    public SearchListViewHolder(@NonNull View itemView) {
        super(itemView);
            image = itemView.findViewById(R.id.image);
        name = itemView.findViewById(R.id.name);
        nationalId = itemView.findViewById(R.id.nationalId);
        mobile = itemView.findViewById(R.id.mobile);
        reportedByName = itemView.findViewById(R.id.reportedByName);
        searchListLayout = itemView.findViewById(R.id.searchListLayout);
    }
}