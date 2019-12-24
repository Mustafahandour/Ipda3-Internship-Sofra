package com.example.sofra.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegionData;
import com.example.sofra.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.ViewHolder> {

    public BaseActivity activity;
    private List<RegionData> RegionDataList = new ArrayList<>();
    private LayoutInflater inflater;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_empty_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder,position);
        setAction(holder,position);


    }

    private void setData(ViewHolder holder, int position) {

    }

    private void setAction(ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);

        }
    }
}
