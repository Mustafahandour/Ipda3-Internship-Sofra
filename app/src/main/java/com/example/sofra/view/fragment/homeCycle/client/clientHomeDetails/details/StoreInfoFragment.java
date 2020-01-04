package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StoreInfoFragment extends BaseFragment {

    public String state;
    public String city;
    public String region;
    public String minimumCharge;
    public String deliveryCost;
    @BindView(R.id.fragment_store_info_state)
    TextView fragmentStoreInfoState;
    @BindView(R.id.fragment_store_info_city)
    TextView fragmentStoreInfoCity;
    @BindView(R.id.fragment_store_info_region)
    TextView fragmentStoreInfoRegion;
    @BindView(R.id.fragment_store_info_minimumCharge)
    TextView fragmentStoreInfoMinimumCharge;
    @BindView(R.id.fragment_store_info_deliveryCost)
    TextView fragmentStoreInfoDeliveryCost;
    private Unbinder unbinder;

    public StoreInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        fragmentStoreInfoState.setText(getString(R.string.state)+"   "+ state);
        fragmentStoreInfoCity.setText(getString(R.string.city)+"   "+ city);
        fragmentStoreInfoCity.setText(getString(R.string.region)+"   "+ city);
        fragmentStoreInfoCity.setText(getString(R.string.minimum_order)+"   "+ minimumCharge);
        fragmentStoreInfoCity.setText(getString(R.string.delivery_cost)+"   "+ deliveryCost);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

