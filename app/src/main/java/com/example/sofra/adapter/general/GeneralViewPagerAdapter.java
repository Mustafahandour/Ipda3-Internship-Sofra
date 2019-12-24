package com.example.sofra.adapter.general;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.sofra.R;
import com.example.sofra.data.model.register.RegionData;
import com.example.sofra.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GeneralViewPagerAdapter extends FragmentPagerAdapter {

    Context mContext;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> fragmentTitle = new ArrayList<>();

    @SuppressLint("WrongConstant")
    public GeneralViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, 1);
        this.fragments = new ArrayList<>();
        this.fragmentTitle = new ArrayList<>();
    }

    public void addPager(Fragment fragment, String title) {
        this.fragments.add(fragment);
        this.fragmentTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}