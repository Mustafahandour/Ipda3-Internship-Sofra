package com.example.sofra.view.fragment.authCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.sofra.R;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.GeneralRequest.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.replace;

public class RegisterRestaurantStep1Fragment extends BaseFragment {


    @BindView(R.id.restaurant_register_fragment_step1_ti_restaurant_name)
    TextInputLayout restaurantRegisterFragmentStep1TiRestaurantName;
    @BindView(R.id.restaurant_register_fragment_step1_ti_email)
    TextInputLayout restaurantRegisterFragmentStep1TiEmail;
    @BindView(R.id.restaurant_register_fragment_step1_ti_delivery_time)
    TextInputLayout restaurantRegisterFragmentStep1TiDeliveryTime;
    @BindView(R.id.restaurant_register_fragment_step1_sp_city)
    Spinner restaurantRegisterFragmentStep1SpCity;
    @BindView(R.id.restaurant_register_fragment_step1_sp_region)
    Spinner restaurantRegisterFragmentStep1SpRegion;
    @BindView(R.id.restaurant_register_fragment_step1_rl_region)
    RelativeLayout restaurantRegisterFragmentStep1RlRegion;
    @BindView(R.id.restaurant_register_fragment_step1_ti_password)
    TextInputLayout restaurantRegisterFragmentStep1TiPassword;
    @BindView(R.id.restaurant_register_fragment_step1_ti_confirm_password)
    TextInputLayout restaurantRegisterFragmentStep1TiConfirmPassword;
    @BindView(R.id.restaurant_register_fragment_step1_ti_minimum_order)
    TextInputLayout restaurantRegisterFragmentStep1TiMinimumOrder;
    @BindView(R.id.restaurant_register_fragment_step1_ti_delivery_cost)
    TextInputLayout restaurantRegisterFragmentStep1TiDeliveryCost;
    @BindView(R.id.restaurant_register_fragment_step1_bt_follow)
    Button restaurantRegisterFragmentStep1BtFollow;
    private CustomSpinnerAdapter cityAdapter, regionAdapter;

    public RegisterRestaurantStep1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_restaurant_step1, container, false);
        initFragment();
        ButterKnife.bind(this, view);
        restaurantRegisterFragmentStep1RlRegion.setVisibility(View.GONE);

        cityAdapter = new CustomSpinnerAdapter(getActivity());
        regionAdapter = new CustomSpinnerAdapter(getActivity());

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    restaurantRegisterFragmentStep1RlRegion.setVisibility(View.GONE);

                } else {
                    getSpinnerData(restaurantRegisterFragmentStep1SpRegion, regionAdapter, getString(R.string.select_your_region)
                            , getClient().getRegion(cityAdapter.selectedId), restaurantRegisterFragmentStep1RlRegion);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getSpinnerData(restaurantRegisterFragmentStep1SpCity, cityAdapter, getString(R.string.select_your_city), getClient().getCity(), listener);
        return view;
    }

    @OnClick({R.id.restaurant_register_fragment_step1_bt_follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.restaurant_register_fragment_step1_bt_follow:

                String name = restaurantRegisterFragmentStep1TiRestaurantName.getEditText().getText().toString().trim();
                String email = restaurantRegisterFragmentStep1TiEmail.getEditText().getText().toString().trim();
                String password = restaurantRegisterFragmentStep1TiPassword.getEditText().getText().toString().trim();
                String passwordConfirmation = restaurantRegisterFragmentStep1TiConfirmPassword.getEditText().getText().toString().trim();
                String regionId = String.valueOf(regionAdapter.selectedId);
                String deliveryCost = restaurantRegisterFragmentStep1TiDeliveryCost.getEditText().getText().toString().trim();
                String minimumCharge = restaurantRegisterFragmentStep1TiMinimumOrder.getEditText().getText().toString().trim();
                String deliveryTime = restaurantRegisterFragmentStep1TiDeliveryTime.getEditText().getText().toString().trim();


                RegisterRestaurantStep2Fragment registerRestaurantStep2Fragment = new RegisterRestaurantStep2Fragment();
                registerRestaurantStep2Fragment.name = name;
                registerRestaurantStep2Fragment.email = email;
                registerRestaurantStep2Fragment.password = password;
                registerRestaurantStep2Fragment.passwordConfirmation = passwordConfirmation;
                registerRestaurantStep2Fragment.regionId = regionId;
                registerRestaurantStep2Fragment.deliveryCost = deliveryCost;
                registerRestaurantStep2Fragment.minimumCharge = minimumCharge;
                registerRestaurantStep2Fragment.deliveryTime = deliveryTime;


                replace(registerRestaurantStep2Fragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);
                break;
        }
    }
}

