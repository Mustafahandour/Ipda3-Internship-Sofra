package com.example.sofra.view.fragment.homeCycle.restaurant.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sofra.R;


public class RestaurantProfile2Fragment extends Fragment {


    public String name;
    public String email;
    public String regionId;
    public String minimumCharge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile2_restaurant, container, false);

        return root;
    }
}