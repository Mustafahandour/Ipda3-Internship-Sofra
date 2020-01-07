package com.example.sofra.view.fragment.authCycle;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.view.activity.HomeActivity;
import com.example.sofra.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.GeneralRequest.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.openGallery;

public class RegisterClientFragment extends BaseFragment {

    @BindView(R.id.client_register_fragment_ib_profile_pic)
    CircleImageView clientRegisterFragmentIbProfilePic;
    @BindView(R.id.client_register_fragment_ti_name)
    TextInputLayout clientRegisterFragmentTiName;
    @BindView(R.id.client_register_fragment_ti_email)
    TextInputLayout clientRegisterFragmentTiEmail;
    @BindView(R.id.client_register_fragment_ti_phone)
    TextInputLayout clientRegisterFragmentTiPhone;
    @BindView(R.id.client_register_fragment_sp_city)
    Spinner clientRegisterFragmentSpCity;
    @BindView(R.id.client_register_fragment_sp_region)
    Spinner clientRegisterFragmentSpRegion;
    @BindView(R.id.client_register_fragment_ti_password)
    TextInputLayout clientRegisterFragmentTiPassword;
    @BindView(R.id.client_register_fragment_ti_confirm_password)
    TextInputLayout clientRegisterFragmentTiConfirmPassword;
    @BindView(R.id.client_register_fragment_bt_login)
    Button clientRegisterFragmentBtLogin;
    @BindView(R.id.client_register_fragment_rl_region)
    RelativeLayout clientRegisterFragmentRlRegion;
    private CustomSpinnerAdapter regionAdapter, cityAdapter;
    private String path;
    private ArrayList<AlbumFile> images = new ArrayList<>();

    public RegisterClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_client, container, false);
        ButterKnife.bind(this, view);

        clientRegisterFragmentRlRegion.setVisibility(View.GONE);

        cityAdapter = new CustomSpinnerAdapter(getActivity());
        regionAdapter = new CustomSpinnerAdapter(getActivity());

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    clientRegisterFragmentRlRegion.setVisibility(View.GONE);

                } else {
                    getSpinnerData(clientRegisterFragmentSpRegion, regionAdapter, getString(R.string.select_your_region)
                            , getClient().getRegion(cityAdapter.selectedId), clientRegisterFragmentRlRegion);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getSpinnerData(clientRegisterFragmentSpCity, cityAdapter, getString(R.string.select_your_city), getClient().getCity(), listener);

        return view;
    }


    @OnClick({R.id.client_register_fragment_rl_profile_pic, R.id.client_register_fragment_bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_register_fragment_rl_profile_pic:
                openGallery(getActivity(), 1, images, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        images.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(clientRegisterFragmentIbProfilePic, path, getActivity());
                    }
                });

                break;


            case R.id.client_register_fragment_bt_login:

                getClientRegister();
                break;
        }
    }

    private void getClientRegister() {
        String name = clientRegisterFragmentTiName.getEditText().getText().toString();
        String email = clientRegisterFragmentTiEmail.getEditText().getText().toString();
        String password = clientRegisterFragmentTiPassword.getEditText().getText().toString();
        String passwordConfirmation = clientRegisterFragmentTiConfirmPassword.getEditText().getText().toString();
        String phone = clientRegisterFragmentTiPhone.getEditText().getText().toString();
        String regionId = String.valueOf(regionAdapter.selectedId);


        getClient().getRegisterClient(convertToRequestBody(name), convertToRequestBody(email),
                convertToRequestBody(password), convertToRequestBody(passwordConfirmation),
                convertToRequestBody(phone), convertToRequestBody(regionId),
                convertFileToMultipart(path, "profile_image")).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
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



}

