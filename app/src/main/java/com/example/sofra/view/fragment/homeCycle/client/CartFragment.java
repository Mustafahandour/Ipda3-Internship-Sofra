package com.example.sofra.view.fragment.homeCycle.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.CartAdapter;
import com.example.sofra.data.local.room.Item;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.fragment.BaseFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.ConfirmClientOrderFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.data.local.room.RoomManger.getInstance;
import static com.example.sofra.helper.HelperMethod.replace;

public class CartFragment extends BaseFragment {

    @BindView(R.id.fragment_cart_rv)
    RecyclerView fragmentCartRv;
    @BindView(R.id.fragment_cart_tv_t_cost)
    TextView fragmentCartTvTCost;
    @BindView(R.id.fragment_cart_bt_confirm)
    Button fragmentCartBtConfirm;
    @BindView(R.id.fragment_cart_bt_more)
    Button fragmentCartBtMore;
    private Unbinder unbinder;
    private List<Item> items;
    private RoomDao roomDao;
    private double total;
    private ConfirmClientOrderFragment confirmClientOrderFragment;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();

        return view;
    }

    private void getData() {


        roomDao = getInstance(getActivity()).roomDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                items = roomDao.getAll();


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        fragmentCartRv.setLayoutManager(linearLayoutManager);
                        CartAdapter cartAdapter = new CartAdapter(getActivity(), items);
                        fragmentCartRv.setAdapter(cartAdapter);

                        total = 0.0;
                        for (int i = 0; i < items.size(); i++) {
                            total = total + items.get(i).getQuantity() * items.get(i).getCost();
                        }
                        fragmentCartTvTCost.setText(String.valueOf(total));

                    }
                });


            }
        });
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

    @OnClick({R.id.fragment_cart_bt_confirm, R.id.fragment_cart_bt_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_cart_bt_confirm:
                 confirmClientOrderFragment = new ConfirmClientOrderFragment();
                 confirmClientOrderFragment.itemData = items;
                replace(confirmClientOrderFragment,getActivity().getSupportFragmentManager(),R.id.nav_host_fragment);
                break;
            case R.id.fragment_cart_bt_more:
                break;
        }
    }

    public void updateUi(List<Item> itemList) {
        for (int i = 0; i < itemList.size(); i++) {
            total = 0.0 + itemList.get(i).getQuantity() * itemList.get(i).getCost();
        }
        fragmentCartTvTCost.setText(String.valueOf(total));

    }

    }


