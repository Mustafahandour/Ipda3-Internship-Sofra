package com.example.sofra.view.fragment.homeCycle.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.model.contactUs.ContactUs;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.fragment_contact_us_et_name)
    EditText fragmentContactUsEtName;
    @BindView(R.id.fragment_contact_us_et_mail)
    EditText fragmentContactUsEtMail;
    @BindView(R.id.fragment_contact_us_et_phone)
    EditText fragmentContactUsEtPhone;
    @BindView(R.id.fragment_contact_us_et_message)
    EditText fragmentContactUsEtMessage;
    @BindView(R.id.fragment_contact_us_rb_complain)
    RadioButton fragmentContactUsRbComplain;
    @BindView(R.id.fragment_contact_us_rb_suggest)
    RadioButton fragmentContactUsRbSuggest;
    @BindView(R.id.fragment_contact_us_rb_Query)
    RadioButton fragmentContactUsRbQuery;
    @BindView(R.id.fragment_contact_us_bt_send)
    Button fragmentContactUsBtSend;
    @BindView(R.id.fragment_contact_us_rg)
    RadioGroup fragmentContactUsRg;
    private Unbinder unbinder;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
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

    @OnClick(R.id.fragment_contact_us_bt_send)
    public void onViewClicked() {

        String type;
        if (fragmentContactUsRbSuggest.isChecked()) {
            type = fragmentContactUsRbSuggest.getText().toString();

        }else if (fragmentContactUsRbComplain.isChecked()){
            type = fragmentContactUsRbComplain.getText().toString();
        }else {

            type = fragmentContactUsRbQuery.getText().toString();

        }
        String name = fragmentContactUsEtName.getText().toString();
        String mail = fragmentContactUsEtMail.getText().toString();
        String phone = fragmentContactUsEtPhone.getText().toString();
        String content = fragmentContactUsEtMessage.getText().toString();
        getContact(name, mail, phone,type, content);
    }



    private void getContact(String name, String mail, String phone, String type, String content) {
        getClient().getContact(name, mail, phone, type, content).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }
}

