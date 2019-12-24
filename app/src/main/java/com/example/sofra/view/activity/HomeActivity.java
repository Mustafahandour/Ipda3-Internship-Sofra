package com.example.sofra.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sofra.R;
import com.example.sofra.view.fragment.homeCycle.client.clientMore.MoreClientFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.restaurantMore.MoreRestaurantFragment;
import com.example.sofra.view.fragment.homeCycle.general.OrderListFragment;
import com.example.sofra.view.fragment.homeCycle.client.ClientProfileFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.profile.RestaurantProfileFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.ClientHomeFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.RestaurantHomeFragment;
import com.example.sofra.view.fragment.homeCycle.general.NotificationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;
import static com.example.sofra.helper.HelperMethod.replace;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.activity_home_ib_notification)
    ImageButton activityHomeIbNotification;
    @BindView(R.id.activity_home_tv_title)
    TextView activityHomeTvTitle;
    @BindView(R.id.activity_home_ib_shopping_cart)
    ImageButton activityHomeIbShoppingCart;
    @BindView(R.id.activity_home_tv_home)
    TextView activityHomeTvHome;
    @BindView(R.id.activity_home_tv_list)
    TextView activityHomeTvList;
    @BindView(R.id.activity_home_tv_profile)
    TextView activityHomeTvProfile;
    @BindView(R.id.activity_home_tv_more)
    TextView activityHomeTvMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
     gethome();

    }

    private void gethome() {
        String userType = LoadData(this, USER_TYPE);
        if (userType.equals(USER_TYPE_RESTAURANT)) {
            RestaurantHomeFragment restaurantHomeFragment = new RestaurantHomeFragment();
            replace(restaurantHomeFragment,getSupportFragmentManager(),R.id.nav_host_fragment);
        }else {
            ClientHomeFragment restaurantListFragment = new ClientHomeFragment();
            replace(restaurantListFragment, getSupportFragmentManager(), R.id.nav_host_fragment);
        }
    }

    @OnClick({R.id.activity_home_ib_notification, R.id.activity_home_ib_shopping_cart, R.id.activity_home_tv_home, R.id.activity_home_tv_list, R.id.activity_home_tv_profile, R.id.activity_home_tv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_home_ib_notification:
                NotificationFragment notificationFragment = new NotificationFragment();
                replace(notificationFragment, getSupportFragmentManager(), R.id.nav_host_fragment);
                break;
            case R.id.activity_home_ib_shopping_cart:
                break;
            case R.id.activity_home_tv_home:


                   gethome();

                break;
            case R.id.activity_home_tv_list:
                OrderListFragment OrderListFragment = new OrderListFragment();
                replace(OrderListFragment,getSupportFragmentManager(),R.id.nav_host_fragment);
                break;
            case R.id.activity_home_tv_profile:
                try {
                    if (LoadData(this,USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
                        RestaurantProfileFragment restaurantProfileFragment = new RestaurantProfileFragment();
                        replace(restaurantProfileFragment,getSupportFragmentManager(),R.id.nav_host_fragment);
                    }
                    ClientProfileFragment clientProfileFragment = new ClientProfileFragment();
                    replace(clientProfileFragment,getSupportFragmentManager(),R.id.nav_host_fragment);
                }catch (Exception e){

                }
                break;
            case R.id.activity_home_tv_more:
                try {
                    if (LoadData(this,USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
                        MoreRestaurantFragment moreRestaurantFragment = new MoreRestaurantFragment();
                        replace(moreRestaurantFragment,getSupportFragmentManager(),R.id.nav_host_fragment);
                    }
                    MoreClientFragment moreClientFragment = new MoreClientFragment();
                    replace(moreClientFragment,getSupportFragmentManager(),R.id.nav_host_fragment);

                }catch (Exception e){

                }
                break;
        }
    }
}
