package com.example.sofra.view.fragment.homeCycle.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sofra.R;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.data.model.register.RegisterDataRestaurant;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.HomeActivity;
import com.example.sofra.view.fragment.BaseFragment;
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

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.loadClientData;
import static com.example.sofra.data.local.SharedPreferencesManger.saveClientData;
import static com.example.sofra.helper.GeneralRequest.getSpinnerData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.openGallery;


public class ClientProfileFragment extends BaseFragment {

    @BindView(R.id.client_profile_fragment_ib_profile_pic)
    CircleImageView clientProfileFragmentIbProfilePic;
    @BindView(R.id.client_profile_fragment_et_name)
    EditText clientProfileFragmentEtName;
    @BindView(R.id.client_profile_fragment_et_mail)
    EditText clientProfileFragmentEtMail;
    @BindView(R.id.client_profile_fragment_et_phone)
    EditText clientProfileFragmentEtPhone;
    @BindView(R.id.client_profile_fragment_sp_city)
    Spinner clientProfileFragmentSpCity;
    @BindView(R.id.client_profile_fragment_sp_region)
    Spinner clientProfileFragmentSpRegion;
    @BindView(R.id.client_profile_fragment_bt_edit)
    Button clientProfileFragmentBtEdit;
    private CustomSpinnerAdapter cityAdapter;
    private CustomSpinnerAdapter regionAdapter;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;
    private RegisterDataRestaurant registerDataRestaurant;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_client, container, false);
        ButterKnife.bind(this, view);

        getProfile();
        return view;
    }

    private void getProfile() {

        registerDataRestaurant = loadClientData(getActivity(), CLIENT_DATA);

        clientProfileFragmentEtName.setText(registerDataRestaurant.getUserRestaurant().getName());
        clientProfileFragmentEtMail.setText(registerDataRestaurant.getUserRestaurant().getEmail());
        clientProfileFragmentEtPhone.setText(registerDataRestaurant.getUserRestaurant().getPhone());

        onLoadImageFromUrl(clientProfileFragmentIbProfilePic, registerDataRestaurant.getUserRestaurant().getPhotoUrl(), getActivity());

        cityAdapter = new CustomSpinnerAdapter(getActivity());
        regionAdapter = new CustomSpinnerAdapter(getActivity());
        getSpinnerData(clientProfileFragmentSpCity, cityAdapter, getString(R.string.city), getClient().getCity(), registerDataRestaurant.getUserRestaurant().getRegion().getCity().getId(), new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getSpinnerData(clientProfileFragmentSpRegion, regionAdapter, getString(R.string.region),
                            getClient().getRegion(cityAdapter.selectedId), registerDataRestaurant.getUserRestaurant().getRegion().getId());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick({R.id.client_profile_fragment_ib_profile_pic, R.id.client_profile_fragment_bt_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_profile_fragment_ib_profile_pic:
                openGallery(getActivity(), 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(clientProfileFragmentIbProfilePic, path, getActivity());

                    }
                });
                break;
            case R.id.client_profile_fragment_bt_edit:
                editProfile();
                break;
        }
    }

    private void editProfile() {
        String name = clientProfileFragmentEtName.getText().toString();
        String email = clientProfileFragmentEtMail.getText().toString();
        String phone = clientProfileFragmentEtPhone.getText().toString();
        String regionId = String.valueOf(regionAdapter.selectedId);


        getClient().profileClientEdit(convertToRequestBody(registerDataRestaurant.getApiToken()), convertToRequestBody(name), convertToRequestBody(email),
                convertToRequestBody(phone), convertToRequestBody(regionId),
                convertFileToMultipart(path, "profile_image")).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        saveClientData(getActivity(), response.body().getData());
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