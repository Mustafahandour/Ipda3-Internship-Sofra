package com.example.sofra.view.fragment.homeCycle.restaurant.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sofra.R;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.data.model.register.RegisterDataRestaurant;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.helper.GeneralRequest;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.RESTAURANT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.loadRestaurantData;
import static com.example.sofra.data.local.SharedPreferencesManger.saveRestaurantData;
import static com.example.sofra.helper.GeneralRequest.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.openGallery;


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
    @BindView(R.id.restaurant_profile_fragment_et_delivery_cost)
    EditText restaurantProfileFragmentEtDeliveryCost;
    @BindView(R.id.restaurant_profile_fragment_et_delivery_time)
    EditText restaurantProfileFragmentEtDeliveryTime;
    @BindView(R.id.restaurant_profile_fragment_switch_state)
    Switch restaurantProfileFragmentSwitchState;
    @BindView(R.id.restaurant_profile_fragment_et_phone)
    EditText restaurantProfileFragmentEtPhone;
    @BindView(R.id.restaurant_profile_fragment_et_whatsApp)
    EditText restaurantProfileFragmentEtWhatsApp;
    @BindView(R.id.restaurant_profile_fragment_bt_edit)
    Button restaurantProfileFragmentBtEdit;
    private Unbinder unbinder;
    private CustomSpinnerAdapter cityAdapter, regionAdapter;
    private RegisterDataRestaurant registerDataRestaurant;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;
    private String state;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        cityAdapter = new CustomSpinnerAdapter(getActivity());
        regionAdapter = new CustomSpinnerAdapter(getActivity());


        showProfile();
        return view;
    }

    private void showProfile() {


        registerDataRestaurant = loadRestaurantData(getActivity(), RESTAURANT_DATA);


        restaurantProfileFragmentEtName.setText(registerDataRestaurant.getUserRestaurant().getName());
        restaurantProfileFragmentEtMail.setText(registerDataRestaurant.getUserRestaurant().getEmail());
        restaurantProfileFragmentEtMinimumCharge.setText(registerDataRestaurant.getUserRestaurant().getMinimumCharger());
        onLoadImageFromUrl(restaurantProfileFragmentIbProfilePic, registerDataRestaurant.getUserRestaurant().getPhotoUrl(), getActivity());
        restaurantProfileFragmentEtDeliveryCost.setText(registerDataRestaurant.getUserRestaurant().getDeliveryCost());
        restaurantProfileFragmentEtDeliveryTime.setText(registerDataRestaurant.getUserRestaurant().getDeliveryTime());
        restaurantProfileFragmentEtPhone.setText(registerDataRestaurant.getUserRestaurant().getPhone());
        restaurantProfileFragmentEtWhatsApp.setText(registerDataRestaurant.getUserRestaurant().getWhatsapp());
        if (registerDataRestaurant.getUserRestaurant().getAvailability().equals("open")) {
            restaurantProfileFragmentSwitchState.setChecked(true);
        } else {
            restaurantProfileFragmentSwitchState.setChecked(false);
        }
        getSpinnerData(restaurantProfileFragmentSpCity, cityAdapter, getString(R.string.city), getClient().getCity(),
                Integer.parseInt(registerDataRestaurant.getUserRestaurant().getRegion().getCityId()), new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {

                            getSpinnerData(restaurantProfileFragmentSpRegion, regionAdapter, getString(R.string.region), getClient().getRegion(cityAdapter.selectedId),
                                    Integer.parseInt(registerDataRestaurant.getUserRestaurant().getRegionId()));

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }


    @OnClick({R.id.restaurant_profile_fragment_ib_profile_pic, R.id.restaurant_profile_fragment_bt_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_profile_fragment_ib_profile_pic:

                openGallery(getActivity(), 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(restaurantProfileFragmentIbProfilePic, path, getActivity());
                    }
                });
                break;
            case R.id.restaurant_profile_fragment_bt_edit:
                getProfileEdit();
                break;
        }
    }

    private void getProfileEdit() {

        String name = restaurantProfileFragmentEtName.getText().toString();
        String mail = restaurantProfileFragmentEtMail.getText().toString();
        String phone = restaurantProfileFragmentEtPhone.getText().toString();
        String whatsApp = restaurantProfileFragmentEtWhatsApp.getText().toString();
        String regionId = String.valueOf(regionAdapter.selectedId);
        String deliveryCost = restaurantProfileFragmentEtDeliveryCost.getText().toString();
        String minimumCharge = restaurantProfileFragmentEtMinimumCharge.getText().toString();
        String deliveryTime = restaurantProfileFragmentEtDeliveryTime.getText().toString();
        if (restaurantProfileFragmentSwitchState.isChecked()) {
            state = "open";
        } else {
            state = "closed";
        }
        getClient().profileRestaurantEdit(convertToRequestBody(registerDataRestaurant.getApiToken()), convertToRequestBody(name),
                convertToRequestBody(mail), convertToRequestBody(phone), convertToRequestBody(whatsApp), convertToRequestBody(regionId),
                convertToRequestBody(deliveryCost), convertToRequestBody(minimumCharge), convertToRequestBody(state),
                convertFileToMultipart(path, "photo"), convertToRequestBody(deliveryTime)).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        saveRestaurantData(getActivity(), response.body().getData());
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RegisterRestaurant> call, Throwable t) {

            }
        });

    }
}