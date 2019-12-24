package com.example.sofra.view.activity;

import android.os.Bundle;

import com.example.sofra.R;
import com.example.sofra.view.fragment.authCycle.LoginFragment;

import static com.example.sofra.helper.HelperMethod.replace;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginFragment loginFragment = new LoginFragment();
        replace(loginFragment, getSupportFragmentManager(), R.id.activity_fl_container);
    }
}
