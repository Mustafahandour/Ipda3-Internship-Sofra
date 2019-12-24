package com.example.sofra.view.fragment.authCycle;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegisterRestaurant;
import com.example.sofra.view.activity.HomeActivity;
import com.example.sofra.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.saveClientData;
import static com.example.sofra.data.local.SharedPreferencesManger.saveRestaurantData;
import static com.example.sofra.helper.HelperMethod.dismissProgressDialog;
import static com.example.sofra.helper.HelperMethod.replace;
import static com.example.sofra.helper.HelperMethod.showProgressDialog;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.login_fragment_ti_email)
    TextInputLayout loginFragmentTiEmail;
    @BindView(R.id.login_fragment_ti_password)
    TextInputLayout loginFragmentTiPassword;
    @BindView(R.id.login_fragment_tv_forget_password)
    TextView loginFragmentTvForgetPassword;
    @BindView(R.id.login_fragment_bt_login)
    Button loginFragmentBtLogin;
    @BindView(R.id.login_fragment_tv_here)
    TextView loginFragmentTvHere;
    private Unbinder unbinder;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initFragment();
        unbinder = ButterKnife.bind(this, view);
//
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_fragment_tv_forget_password, R.id.login_fragment_bt_login, R.id.login_fragment_tv_here})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_fragment_tv_forget_password:
                ResetPassWordFragment resetPassWordFragment = new ResetPassWordFragment();
                replace(resetPassWordFragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);
                break;
            case R.id.login_fragment_bt_login:
                String mail = loginFragmentTiEmail.getEditText().getText().toString();
                String password = loginFragmentTiPassword.getEditText().getText().toString();
                if (LoadData(getActivity(), USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
                    getRestaurantLogin(mail, password);
                } else {
                    getClientLogin(mail, password);
                }

                break;
            case R.id.login_fragment_tv_here:
                if (LoadData(getActivity(), USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
                    RegisterRestaurantStep1Fragment registerRestaurantStep1Fragment = new RegisterRestaurantStep1Fragment();
                    replace(registerRestaurantStep1Fragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);
                } else {
                    RegisterClientFragment registerClientFragment = new RegisterClientFragment();
                    replace(registerClientFragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);

                }
                break;
        }
    }

    private void getClientLogin(String mail, String password) {
        showProgressDialog(getActivity(), getString(R.string.please_wait));

        getClient().getClientLogin(mail, password).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                dismissProgressDialog();
                try {

                    if (response.body().getStatus() == 1) {
                        saveClientData(getActivity(), response.body().getData().getUserRestaurant());
                        Intent intent = new Intent(getActivity(),HomeActivity.class);
                        startActivity(intent);

                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RegisterRestaurant> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }

    private void getRestaurantLogin(String mail, String password) {
        showProgressDialog(getActivity(), getString(R.string.please_wait));
        getClient().getRestaurantLogin(mail, password).enqueue(new Callback<RegisterRestaurant>() {
            @Override
            public void onResponse(Call<RegisterRestaurant> call, Response<RegisterRestaurant> response) {
                dismissProgressDialog();
                try {

                    if (response.body().getStatus() == 1) {
                        saveRestaurantData(getActivity(), response.body().getData().getUserRestaurant());
                        SaveData(getActivity(), "Restaurant_Id",response.body().getData().getUserRestaurant().getId());
                        SaveData(getActivity(), "Restaurant_ApiToken",response.body().getData().getApiToken());

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);

                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RegisterRestaurant> call, Throwable t) {
                dismissProgressDialog();
            }
        });
    }
}

