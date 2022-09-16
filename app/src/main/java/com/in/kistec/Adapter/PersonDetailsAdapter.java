package com.in.kistec.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonDetailsAdapter  extends RecyclerView.Adapter<PersonDetailsViewHolder> {
    @NonNull

    @Override
    public PersonDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull  PersonDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class PersonDetailsViewHolder extends RecyclerView.ViewHolder {

    public PersonDetailsViewHolder(@NonNull  View itemView) {
        super(itemView);
    }
}
