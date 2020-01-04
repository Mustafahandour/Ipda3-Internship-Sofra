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
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.data.model.register.UserDataRestaurant;
import com.example.sofra.helper.HelperMethod;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.replace;


public class RestaurantProfileFragment extends Fragment {


    @BindView(R.id.client_profile_fragment_ib_profile_pic)
    CircleImageView clientProfileFragmentIbProfilePic;
    @BindView(R.id.client_profile_fragment_et_name)
    EditText clientProfileFragmentEtName;
    @BindView(R.id.client_profile_fragment_et_mail)
    EditText clientProfileFragmentEtMail;
    @BindView(R.id.client_profile_fragment_sp_city)
    Spinner clientProfileFragmentSpCity;
    @BindView(R.id.client_profile_fragment_sp_region)
    Spinner clientProfileFragmentSpRegion;
    @BindView(R.id.client_profile_fragment_et_minimum_charge)
    EditText clientProfileFragmentEtMinimumCharge;
    @BindView(R.id.client_profile_fragment_bt_follow)
    Button clientProfileFragmentBtFollow;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_restaurant, container, false);
           showProfile();
        return view;
    }

    private void showProfile() {





//
//                clientProfileFragmentEtName.setText( LoadData(getActivity(), "Restaurant_Name"));
//                clientProfileFragmentEtMail.setText(   LoadData(getActivity(), "Restaurant_Mail"));
//                clientProfileFragmentSpCity.setSelection(Integer.parseInt(LoadData(getActivity(), "Restaurant_City")));
//                clientProfileFragmentSpRegion.setSelection(Integer.parseInt(LoadData(getActivity(), "Restaurant_Region")));
//                clientProfileFragmentEtMinimumCharge.setText(LoadData(getActivity(), "Restaurant_MinimumCharger"));
//                onLoadImageFromUrl(clientProfileFragmentIbProfilePic, LoadData(getActivity(), "Restaurant_Photo"),getActivity());

    }

    @OnClick(R.id.client_profile_fragment_bt_follow)
    public void onViewClicked() {
//        String name = clientProfileFragmentEtName.getText().toString().trim();
//        String email = clientProfileFragmentEtMail.getText().toString().trim();
//        String regionId = String.valueOf(getClient().getRegion(clientProfileFragmentSpRegion.getSelectedItemPosition()));
//        String minimumCharge = clientProfileFragmentEtMinimumCharge.getText().toString().trim();

RestaurantProfile2Fragment restaurantProfile2Fragment =new RestaurantProfile2Fragment();
//        restaurantProfile2Fragment.name = name;
//        restaurantProfile2Fragment.email = email;
//        restaurantProfile2Fragment.regionId = regionId;
//        restaurantProfile2Fragment.minimumCharge = minimumCharge;
        replace(restaurantProfile2Fragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);


    }
}