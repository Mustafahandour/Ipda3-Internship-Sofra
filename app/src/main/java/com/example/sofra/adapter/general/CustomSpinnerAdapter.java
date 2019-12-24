package com.example.sofra.adapter.general;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegionData;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {

    public Context context;
    private List<RegionData> RegionDataList = new ArrayList<>();
    private LayoutInflater inflater;
    public int selectedId = 0;

    public CustomSpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<RegionData> RegionDataList, String hint) {
        this.RegionDataList = new ArrayList<>();
        this.RegionDataList.add(new RegionData(0, hint));
        this.RegionDataList.addAll(RegionDataList);
    }

    @Override
    public int getCount() {
        return RegionDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_custom_spinner, null);

        TextView names = (TextView) view.findViewById(R.id.custom_tv_spinner_adapter);

        names.setText(RegionDataList.get(i).getName());
        selectedId = RegionDataList.get(i).getId();

        return view;
    }
}
