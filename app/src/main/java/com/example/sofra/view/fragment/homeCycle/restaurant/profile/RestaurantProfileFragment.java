package com.example.sofra.view.fragment.homeCycle.restaurant.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegisterDataRestaurant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.loadRestaurantData;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.replace;


public class RestaurantProfileFragment extends Fragment {


    @BindView(R.id.restaurant_profile_fragment_ib_profile_pic)
    CircleImageView restaurantProfileFragmentIbProfilePic;
    @BindView(R.id.restaurant_profile_fragment_et_name)
    EditText restaurantProfileFragmentEtName;
    @BindView(R.id.restaurant_profile_fragment_et_mail)
    EditText restaurantProfileFragmentEtMail;
    @BindView(R.id.restaurant_profile_fragment_sp_city)
    Spinner restaurantProfileFragmentSpCity;
    @BindView(R.id.restaurant_profile_fragment_sp_region)
    Spinner restaurantProfileFragmentSpRegion;
    @BindView(R.id.restaurant_profile_fragment_et_minimum_charge)
    EditText restaurantProfileFragmentEtMinimumCharge;
    @BindView(R.id.restaurant_profile_fragment_bt_follow)
    Button restaurantProfileFragmentBtFollow;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_restaurant, container, false);
        unbinder = ButterKnife.bind(this,view);
        showProfile();
        return view;
    }

    private void showProfile() {


        RegisterDataRestaurant registerDataRestaurant = loadRestaurantData(getActivity(), RESTAURANT_DATA);


        restaurantProfileFragmentEtName.setText(registerDataRestaurant.getUserRestaurant().getName());
        restaurantProfileFragmentEtMail.setText(registerDataRestaurant.getUserRestaurant().getEmail());
        restaurantProfileFragmentEtMinimumCharge.setText(registerDataRestaurant.getUserRestaurant().getMinimumCharger());
        onLoadImageFromUrl(restaurantProfileFragmentIbProfilePic, registerDataRestaurant.getUserRestaurant().getPhotoUrl(), getActivity());
//                clientProfileFragmentSpCity.setSelection();
//                clientProfileFragmentSpRegion.setSelection();

    }






    @OnClick(R.id.restaurant_profile_fragment_bt_follow)
    public void onViewClicked() {
        //        String name = clientProfileFragmentEtName.getText().toString().trim();
//        String email = clientProfileFragmentEtMail.getText().toString().trim();
//        String regionId = String.valueOf(getClient().getRegion(clientProfileFragmentSpRegion.getSelectedItemPosition()));
//        String minimumCharge = clientProfileFragmentEtMinimumCharge.getText().toString().trim();

        RestaurantProfile2Fragment restaurantProfile2Fragment = new RestaurantProfile2Fragment();
//        restaurantProfile2Fragment.name = name;
//        restaurantProfile2Fragment.email = email;
//        restaurantProfile2Fragment.regionId = regionId;
//        restaurantProfile2Fragment.minimumCharge = minimumCharge;
        replace(restaurantProfile2Fragment, getActivity().getSupportFragmentManager(), R.id.nav_host_fragment);

    }
}