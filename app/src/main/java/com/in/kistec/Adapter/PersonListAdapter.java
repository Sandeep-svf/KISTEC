package com.in.kistec.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.kistec.Model.PersonListModel;
import com.in.kistec.R;

import java.util.ArrayList;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListViewHolder> {
    ArrayList<PersonListModel> data;
    Context context;

    public PersonListAdapter(ArrayList<PersonListModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.submited_list_view_holder,parent,false);
        return new PersonListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  PersonListViewHolder holder, int position) {
        holder.status.setText(data.get(position).getStatus());
        holder.personNameList.setText(data.get(position).getPersonNameList());
       holder.usernameRequestedBy.setText(data.get(position).getUsernameRequestedBy());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
class  PersonListViewHolder extends RecyclerView.ViewHolder {
    TextView personNameList, usernameRequestedBy, status;

    public PersonListViewHolder(@NonNull View itemView) {
        super(itemView);
        personNameList = itemView.findViewById(R.id.personNameList);
        usernameRequestedBy = itemView.findViewById(R.id.usernameRequestedBy);
        status = itemView.findViewById(R.id.status);

    }
}
