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

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;


public class RestaurantProfile2Fragment extends Fragment {


    public String name;
    public String email;
    public String regionId;
    public String minimumCharge;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile2_restaurant, container, false);
        getProfile2();
        return root;
    }

    private void getProfile2() {



        restaurantProfile2FragmentEtDeliveryCost.setText(  LoadData(getActivity(), "Restaurant_DeliveryCost"));
        restaurantProfile2FragmentEtDeliveryTime.setText(LoadData(getActivity(), "Restaurant_DeliveryTime"));
        restaurantProfile2FragmentEtPhone.setText(LoadData(getActivity(), "Restaurant_Phone"));
        restaurantProfile2FragmentEtWhatsApp.setText(LoadData(getActivity(), "Restaurant_WhatsApp"));
        if (LoadData(getActivity(), "Restaurant_Availability").equals("open")) {
            restaurantProfile2FragmentSwitchState.isChecked();
        }
    }


    @OnClick(R.id.client_profile_fragment_bt_edit)
    public void onViewClicked() {
    }
}



