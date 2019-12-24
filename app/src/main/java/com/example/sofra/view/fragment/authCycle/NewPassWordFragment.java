package com.example.sofra.view.fragment.authCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class NewPassWordFragment extends BaseFragment {

    @BindView(R.id.new_password_fragment_et_code)
    EditText newPasswordFragmentEtCode;
    @BindView(R.id.new_password_fragment_tv)
    TextView newPasswordFragmentTv;
    @BindView(R.id.new_password_fragment_et_password)
    EditText newPasswordFragmentEtPassword;
    @BindView(R.id.new_password_fragment_et_confirm_password)
    EditText newPasswordFragmentEtConfirmPassword;
    @BindView(R.id.new_password_fragment_bt_login)
    Button newPasswordFragmentBtLogin;
    private Unbinder unbinder;
    public String mail;
    public NewPassWordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.new_password_fragment_bt_login)
    public void onViewClicked() {
        String code = newPasswordFragmentEtCode.getText().toString();
        String password = newPasswordFragmentEtPassword.getText().toString();
        String confirmPassword = newPasswordFragmentEtConfirmPassword.getText().toString();
      try {
          if (LoadData(getActivity(),USER_TYPE).equals(USER_TYPE_RESTAURANT)) {

              getNewRestaurantPassword(code,password,confirmPassword);
          }else {

              getNewClientPassword(code,password,confirmPassword);
          }
      }catch (Exception e){

      }
    }

    private void getNewClientPassword(String code,String password,String confirmPassword) {
        getClient().getNewClientPassword(code, password, confirmPassword).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
              try {
                  if (response.body().getStatus() == 1) {
                      Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                  }else {
                      Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                  }
              }catch (Exception e){

              }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });

    }
    private void getNewRestaurantPassword(String code,String password,String confirmPassword) {
        getClient().getNewRestaurantPassword(code, password, confirmPassword).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });

    }



}

