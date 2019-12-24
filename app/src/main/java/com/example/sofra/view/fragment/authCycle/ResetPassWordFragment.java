package com.example.sofra.view.fragment.authCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofra.R;
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
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;
import static com.example.sofra.helper.HelperMethod.replace;

public class ResetPassWordFragment extends BaseFragment {

    @BindView(R.id.reset_password_fragment_et_email)
    EditText resetPasswordFragmentEtEmail;
    @BindView(R.id.reset_password_fragment_bt_login)
    Button resetPasswordFragmentBtLogin;
    private Unbinder unbinder;
    private static String mail;

    public ResetPassWordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.reset_password_fragment_bt_login)
    public void onViewClicked() {
         mail = resetPasswordFragmentEtEmail.getText().toString();

        try {
            if (LoadData(getActivity(), USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
                getRestaurantPasswordCode(mail);
                NewPassWordFragment newPassWordFragment = new NewPassWordFragment();
                newPassWordFragment.mail = mail;
                replace(newPassWordFragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);
            } else {
                getClientPasswordCode(mail);
                NewPassWordFragment newPassWordFragment = new NewPassWordFragment();
                newPassWordFragment.mail = mail;
                replace(newPassWordFragment, getActivity().getSupportFragmentManager(), R.id.activity_fl_container);

            }
        } catch (Exception e) {

        }


    }

    private void getClientPasswordCode(String mail) {
        getClient().getClientPasswordCode(mail).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(baseActivity, response.body().getData().getCode() + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });
    }

    private void getRestaurantPasswordCode(String mail) {
        getClient().getRestaurantPasswordCode(mail).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        Toast.makeText(baseActivity, response.body().getData().getCode() + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });
    }
}

