package com.example.sofra.view.fragment.homeCycle.restaurant.restaurantMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoreRestaurantFragment extends BaseFragment {

    private Unbinder unbinder;

    public MoreRestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more_restaurant, container, false);
        unbinder = ButterKnife.bind(this,view);

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
}

