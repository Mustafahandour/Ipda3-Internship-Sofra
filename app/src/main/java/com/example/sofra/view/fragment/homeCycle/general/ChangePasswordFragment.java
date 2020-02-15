package com.example.sofra.view.fragment.homeCycle.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.regions.Region;
import com.example.sofra.data.model.register.RegisterDataRestaurant;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.data.model.resetPassword.ResetPassword;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.loadClientData;

public class ChangePasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_change_password_et_old_pass)
    EditText fragmentChangePasswordEtOldPass;
    @BindView(R.id.fragment_change_password_et_new_pass)
    EditText fragmentChangePasswordEtNewPass;
    @BindView(R.id.fragment_change_password_et_confirm_pass)
    EditText fragmentChangePasswordEtConfirmPass;
    @BindView(R.id.fragment_change_password_bt_change)
    Button fragmentChangePasswordBtChange;
    private Unbinder unbinder;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);

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

    @OnClick(R.id.fragment_change_password_bt_change)
    public void onViewClicked() {
        String oldPass = fragmentChangePasswordEtOldPass.getText().toString();
        String newPass = fragmentChangePasswordEtNewPass.getText().toString();
        String confirmPass = fragmentChangePasswordEtConfirmPass.getText().toString();
        String apiTokenRest = LoadData(getActivity(), "Restaurant_ApiToken");
        String apiTokenClient = LoadData(getActivity(), "Client_ApiToken");


        if (LoadData(getActivity(), USER_TYPE).equals(USER_TYPE_RESTAURANT)) {

            getRestaurantPassChange(apiTokenRest, oldPass, newPass, confirmPass);

        } else if (LoadData(getActivity(), USER_TYPE).equals(USER_TYPE_CLIENT)) {
            getClientPassChange(apiTokenClient, oldPass, newPass, confirmPass);
        }


    }

    public void getRestaurantPassChange(String apiTokenRest, String oldPass, String newPass, String confirmPass) {
        getClient().clientPassword(apiTokenRest, oldPass, newPass, confirmPass).enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });

    }

    private void getClientPassChange(String apiTokenRest, String oldPass, String newPass, String confirmPass) {
        getClient().restaurantPassword(apiTokenRest, oldPass, newPass, confirmPass).enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });
    }

}

