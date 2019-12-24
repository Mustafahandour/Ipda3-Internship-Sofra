package com.example.sofra.view.fragment.homeCycle.client;

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
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.loadUserData;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;


public class ClientProfileFragment extends Fragment {

    UserDataRestaurant userDataRestaurant;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_client, container, false);
getProfile();
        return view;
    }

    private void getProfile() {

    }

    @OnClick(R.id.client_profile_fragment_bt_edit)
    public void onViewClicked() {
    }
}