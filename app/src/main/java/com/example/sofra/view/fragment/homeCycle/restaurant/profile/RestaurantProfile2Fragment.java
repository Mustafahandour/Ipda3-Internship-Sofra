package com.example.sofra.view.fragment.homeCycle.restaurant.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegisterDataRestaurant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.loadRestaurantData;


public class RestaurantProfile2Fragment extends Fragment {


    @BindView(R.id.restaurant_profile2_fragment_et_delivery_cost)
    EditText restaurantProfile2FragmentEtDeliveryCost;
    @BindView(R.id.restaurant_profile2_fragment_et_delivery_time)
    EditText restaurantProfile2FragmentEtDeliveryTime;
    @BindView(R.id.restaurant_profile2_fragment_switch_state)
    Switch restaurantProfile2FragmentSwitchState;
    @BindView(R.id.restaurant_profile2_fragment_et_phone)
    EditText restaurantProfile2FragmentEtPhone;
    @BindView(R.id.restaurant_profile2_fragment_et_whatsApp)
    EditText restaurantProfile2FragmentEtWhatsApp;
    @BindView(R.id.client_profile_fragment_bt_edit)
    Button clientProfileFragmentBtEdit;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile2_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);

        getProfile2();
        return view;
    }

    private void getProfile2() {
        RegisterDataRestaurant registerDataRestaurant = loadRestaurantData(getActivity(), RESTAURANT_DATA);


        restaurantProfile2FragmentEtDeliveryCost.setText(registerDataRestaurant.getUserRestaurant().getDeliveryCost());
        restaurantProfile2FragmentEtDeliveryTime.setText(registerDataRestaurant.getUserRestaurant().getDeliveryTime());
        restaurantProfile2FragmentEtPhone.setText(registerDataRestaurant.getUserRestaurant().getPhone());
        restaurantProfile2FragmentEtWhatsApp.setText(registerDataRestaurant.getUserRestaurant().getWhatsapp());
        if (registerDataRestaurant.getUserRestaurant().getActivated().equals("open")) {
            restaurantProfile2FragmentSwitchState.isChecked();
        }
    }



    @OnClick(R.id.client_profile_fragment_bt_edit)
    public void onViewClicked() {
    }
}



