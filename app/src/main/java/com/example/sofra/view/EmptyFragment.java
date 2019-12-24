package com.example.sofra.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofra.R;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EmptyFragment extends BaseFragment {

    private Unbinder unbinder;

    public EmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
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

