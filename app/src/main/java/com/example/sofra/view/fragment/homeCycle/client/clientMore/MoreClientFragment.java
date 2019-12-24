package com.example.sofra.view.fragment.homeCycle.client.clientMore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;
import com.example.sofra.data.model.resetPassword.ResetPassword;
import com.example.sofra.view.activity.SplashActivity;
import com.example.sofra.view.fragment.BaseFragment;
import com.example.sofra.view.fragment.authCycle.ResetPassWordFragment;
import com.example.sofra.view.fragment.homeCycle.general.AboutAppFragment;
import com.example.sofra.view.fragment.homeCycle.general.ContactUsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.helper.HelperMethod.replace;

public class MoreClientFragment extends BaseFragment {

    private static AlertDialog.Builder alertDialogBuilder;
    @BindView(R.id.fragment_more_client_et_offers)
    EditText fragmentMoreClientEtOffers;
    @BindView(R.id.fragment_more_client_et_contact_us)
    EditText fragmentMoreClientEtContactUs;
    @BindView(R.id.fragment_more_client_et_about_app)
    EditText fragmentMoreClientEtAboutApp;
    @BindView(R.id.fragment_more_client_et_change_password)
    EditText fragmentMoreClientEtChangePassword;
    @BindView(R.id.fragment_more_client_et_logout)
    EditText fragmentMoreClientEtLogout;
    ImageButton logoutDialogCancel;
    ImageButton logoutDialogOk;
    private Unbinder unbinder;

    public MoreClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_client, container, false);


        unbinder = ButterKnife.bind(this, view);
     //   getClicked();
getListener(fragmentMoreClientEtOffers);
getListener(fragmentMoreClientEtAboutApp);
getListener(fragmentMoreClientEtContactUs);
        return view;
    }




    private void getListener(EditText editText ) {
        editText.setClickable(true);
        editText.setFocusable(false);
        editText.setInputType(InputType.TYPE_NULL);

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

    @OnClick({R.id.fragment_more_client_et_offers, R.id.fragment_more_client_et_contact_us, R.id.fragment_more_client_et_about_app, R.id.fragment_more_client_et_change_password, R.id.fragment_more_client_et_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_more_client_et_offers:
                ClientOffersFragment offersFragment = new ClientOffersFragment();
                replace(offersFragment, getActivity().getSupportFragmentManager(), R.id.nav_host_fragment);
                break;
            case R.id.fragment_more_client_et_contact_us:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                replace(contactUsFragment, getActivity().getSupportFragmentManager(), R.id.nav_host_fragment);

                break;
            case R.id.fragment_more_client_et_about_app:
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                replace(aboutAppFragment, getActivity().getSupportFragmentManager(), R.id.nav_host_fragment);

                break;
            case R.id.fragment_more_client_et_change_password:
                ResetPassWordFragment resetPassWordFragment = new ResetPassWordFragment();
                replace(resetPassWordFragment,getActivity().getSupportFragmentManager(),R.id.nav_host_fragment);
                break;
            case R.id.fragment_more_client_et_logout:
                openDialog();
                break;
        }
    }

    private void openDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_logout_action);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.logout_dialog_text);
        text.setText(getString(R.string.are_you_sure_to_logout));
        ImageButton accept = (ImageButton) dialog.findViewById(R.id.logout_dialog_ok);
        accept.setImageResource(R.drawable.ic_true);

        // if button is clicked, close the custom dialog
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManger.clean(getActivity());
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
            }
        });
        ImageButton cancel = (ImageButton) dialog.findViewById(R.id.logout_dialog_cancel);
        cancel.setImageResource(R.drawable.ic_wrong);


        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}

