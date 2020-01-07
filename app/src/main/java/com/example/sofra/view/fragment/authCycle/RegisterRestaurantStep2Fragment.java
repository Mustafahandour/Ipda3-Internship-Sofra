package com.example.sofra.view.fragment.authCycle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.view.activity.SplashActivity;
import com.example.sofra.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;
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

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.openGallery;
import static com.example.sofra.helper.HelperMethod.replace;

public class RegisterRestaurantStep2Fragment extends BaseFragment {


    @BindView(R.id.restaurant_register_fragment_step2_ti_phone)
    TextInputLayout restaurantRegisterFragmentStep2TiPhone;
    @BindView(R.id.restaurant_register_fragment_step2_ti_whatsApp)
    TextInputLayout restaurantRegisterFragmentStep2TiWhatsApp;
    @BindView(R.id.restaurant_register_fragment_step2_ci_store_photo)
    CircleImageView restaurantRegisterFragmentStep2CiStorePhoto;
    @BindView(R.id.restaurant_register_fragment_step2_bt_login)
    Button restaurantRegisterFragmentStep2BtLogin;
    private ArrayList<AlbumFile> images = new ArrayList<>();
    private Unbinder unbinder;
    private String path;
    public String name;
    public String email;
    public String password;
    public String passwordConfirmation;
    public String regionId;
    public String deliveryCost;
    public String minimumCharge;
    public String deliveryTime;
    private long Dialog_DISPLAY_LENGTH= 2000;

    public RegisterRestaurantStep2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_restaurant_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.restaurant_register_fragment_step2_ci_store_photo, R.id.restaurant_register_fragment_step2_bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.restaurant_register_fragment_step2_ci_store_photo:

                openGallery(getActivity(), 1, images, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        images.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(restaurantRegisterFragmentStep2CiStorePhoto, path, getActivity());
                    }
                });
                break;
            case R.id.restaurant_register_fragment_step2_bt_login:
                getSellerRegister();
                LoginFragment loginFragment = new LoginFragment();
                replace(loginFragment,getActivity().getSupportFragmentManager(),R.id.nav_host_fragment);
                break;
        }
    }

    private void getSellerRegister() {
        String phone = restaurantRegisterFragmentStep2TiPhone.getEditText().getText().toString().trim();
        String whatsApp = restaurantRegisterFragmentStep2TiWhatsApp.getEditText().getText().toString().trim();

        getClient().getRegisterRestaurant(convertToRequestBody(name), convertToRequestBody(email), convertToRequestBody(password),
                convertToRequestBody(passwordConfirmation), convertToRequestBody(phone),
                convertToRequestBody(whatsApp), convertToRequestBody(regionId), convertToRequestBody(deliveryCost),
                convertToRequestBody(minimumCharge), convertFileToMultipart(path, "photo"),
                convertToRequestBody(deliveryTime)).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<RegisterRestaurant> call, Throwable t) {

            }
        });

    }

//    private void openDialog() {
//
//        // custom dialog
//        final Dialog dialog = new Dialog(getActivity());
//        dialog.setContentView(R.layout.dialog_register_success);
//        dialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//            }
//        }, Dialog_DISPLAY_LENGTH);
//
//
//    }
}

