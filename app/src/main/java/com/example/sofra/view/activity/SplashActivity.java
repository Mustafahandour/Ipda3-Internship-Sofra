package com.example.sofra.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sofra.R;
import com.example.sofra.data.local.SharedPreferencesManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_CLIENT;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;
import static com.example.sofra.data.local.SharedPreferencesManger.saveUserType;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.Splach_activity_bt_orderFood)
    Button SplachActivityBtOrderFood;
    @BindView(R.id.Splach_activity_bt_buyFood)
    Button SplachActivityBtBuyFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.Splach_activity_bt_orderFood, R.id.Splach_activity_bt_buyFood})
    public void onViewClicked(View view) {

        saveUserType(this, null);

        switch (view.getId()) {
            case R.id.Splach_activity_bt_orderFood:
                saveUserType(this, USER_TYPE_CLIENT);
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.Splach_activity_bt_buyFood:
                saveUserType(this, USER_TYPE_RESTAURANT);
                intent = new Intent(this, AuthActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}