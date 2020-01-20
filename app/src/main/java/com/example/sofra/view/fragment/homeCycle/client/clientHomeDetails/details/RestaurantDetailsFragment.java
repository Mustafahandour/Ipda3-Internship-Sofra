package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.general.GeneralViewPagerAdapter;
import com.example.sofra.data.model.register.UserDataRestaurant;
import com.example.sofra.data.model.restaurantList.RestaurantListData;
import com.example.sofra.view.fragment.BaseFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.ConfirmClientOrderFragment;
import com.google.android.material.tabs.TabLayout;

import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;

public class RestaurantDetailsFragment extends BaseFragment {

    public RestaurantListData showRestaurantData;

    public RestaurantDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        ViewPager restaurantDetailsFragmentVpPager = view.findViewById(R.id.restaurant_details_fragment_vp_pager);
        TabLayout restaurantDetailsFragmentTlTab = view.findViewById(R.id.restaurant_details_fragment_tl_tab);

        restaurantDetailsFragmentVpPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(restaurantDetailsFragmentTlTab));
        GeneralViewPagerAdapter generalViewPagerAdapter = new GeneralViewPagerAdapter(getChildFragmentManager());

        // adding fragment to viewpager
        FoodMenuFragment foodMenuFragment = new FoodMenuFragment();
        foodMenuFragment.id = showRestaurantData.getId();
        generalViewPagerAdapter.addPager(foodMenuFragment, getString(R.string.menu_list));

        CommentAndRatingFragment commentAndRatingFragment = new CommentAndRatingFragment();
        commentAndRatingFragment.id = showRestaurantData.getId();
        generalViewPagerAdapter.addPager(commentAndRatingFragment, getString(R.string.comment_rating));

        StoreInfoFragment storeInfoFragment = new StoreInfoFragment();
        storeInfoFragment.state = showRestaurantData.getAvailability();

        storeInfoFragment.minimumCharge =showRestaurantData.getMinimumCharger();
        storeInfoFragment.deliveryCost = showRestaurantData.getDeliveryCost();

        generalViewPagerAdapter.addPager(storeInfoFragment, getString(R.string.store_info));

        restaurantDetailsFragmentVpPager.setAdapter(generalViewPagerAdapter);
        restaurantDetailsFragmentTlTab.setupWithViewPager(restaurantDetailsFragmentVpPager);
        ConfirmClientOrderFragment confirmClientOrderFragment = new ConfirmClientOrderFragment();
        confirmClientOrderFragment.dCost = showRestaurantData.getDeliveryCost();
        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

