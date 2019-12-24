package com.example.sofra.view.fragment.homeCycle.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.adapter.general.GeneralViewPagerAdapter;

import com.example.sofra.view.fragment.homeCycle.client.clientOrder.ClientCurrentOrderFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientOrder.ClientNewOrderFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientOrder.ClientPreviousOrderFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.restaurantOrder.RestaurantCurrentOrderFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.restaurantOrder.RestaurantNewOrderFragment;
import com.example.sofra.view.fragment.homeCycle.restaurant.restaurantOrder.RestaurantPreviousOrderFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;


public class OrderListFragment extends Fragment {


    @BindView(R.id.list_fragment_tl_tab)
    TabLayout listFragmentTlTab;
    @BindView(R.id.list_fragment_vp_pager)
    ViewPager listFragmentVpPager;
    private Unbinder unbinder;
    private GeneralViewPagerAdapter generalViewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        generalViewPagerAdapter = new GeneralViewPagerAdapter(getChildFragmentManager());
      try {
          if (LoadData(getActivity(),USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
             RestaurantNewOrderFragment restaurantNewOrderFragment = new RestaurantNewOrderFragment();
              RestaurantCurrentOrderFragment restaurantCurrentOrderFragment = new RestaurantCurrentOrderFragment();
              RestaurantPreviousOrderFragment restaurantPreviousOrderFragment = new RestaurantPreviousOrderFragment();
              getPage(restaurantNewOrderFragment,restaurantCurrentOrderFragment,restaurantPreviousOrderFragment);
          }else {

              // adding fragment to viewpager
              ClientNewOrderFragment newOrderFragment = new ClientNewOrderFragment();
              ClientCurrentOrderFragment currentOrderFragment = new ClientCurrentOrderFragment();
              ClientPreviousOrderFragment previousOrderFragment = new ClientPreviousOrderFragment();
              getPage(newOrderFragment, currentOrderFragment, previousOrderFragment);
          }
      }catch (Exception e){

      }



        listFragmentVpPager.setAdapter(generalViewPagerAdapter);
        listFragmentTlTab.setupWithViewPager(listFragmentVpPager);
        return view;
    }

    private void getPage(Fragment fragment, Fragment fragment2, Fragment fragment3) {
        generalViewPagerAdapter.addPager(fragment, getString(R.string.new_order));

        generalViewPagerAdapter.addPager(fragment2, getString(R.string.current_order));

        generalViewPagerAdapter.addPager(fragment3, getString(R.string.previous_order));

    }


}