package com.example.sofra.view.fragment.homeCycle.client.clientOrder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.ClientOrderAdapter;
import com.example.sofra.adapter.restaurant.RestaurantOrderAdapter;
import com.example.sofra.data.model.order.Order;
import com.example.sofra.data.model.order.OrderData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE;
import static com.example.sofra.data.local.SharedPreferencesManger.USER_TYPE_RESTAURANT;

public class ClientCurrentOrderFragment extends BaseFragment {

    @BindView(R.id.order_current_fragment_rv)
    RecyclerView orderCurrentFragmentRv;
    private Unbinder unbinder;

    private List<OrderData> currentOrderDataList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage;
    private ClientOrderAdapter currentOrderAdapter;
    private RestaurantOrderAdapter restaurantOrderAdapter;

    public ClientCurrentOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_client, container, false);
        unbinder = ButterKnife.bind(this, view);
        try {
            if (LoadData(getActivity(),USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
             getRestaurantData();
            }
            getClientData();


        }catch (Exception e){

        }
        return view;
    }

    private void getClientData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        orderCurrentFragmentRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getClientCurrentOrder(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        orderCurrentFragmentRv.addOnScrollListener(onEndLess);
        currentOrderAdapter = new ClientOrderAdapter(getActivity(), currentOrderDataList);
        orderCurrentFragmentRv.setAdapter(currentOrderAdapter);
        
        getClientCurrentOrder(1);
    }

    private void getClientCurrentOrder(int page) {
        getClient().getClientOrder("qXc6GqvS1SGjNKfdbyt87EWR2owTCPo80fkQFgIwg9QFjGPx9yDDu9mZzG7M", "current", page).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    currentOrderDataList.addAll(response.body().getData().getData());
                    currentOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
    private void getRestaurantData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        orderCurrentFragmentRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getRestaurantCurrentOrder(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        orderCurrentFragmentRv.addOnScrollListener(onEndLess);
        restaurantOrderAdapter = new RestaurantOrderAdapter(getActivity(), currentOrderDataList);
        orderCurrentFragmentRv.setAdapter(restaurantOrderAdapter);

        getRestaurantCurrentOrder(1);
    }

    private void getRestaurantCurrentOrder(int page) {
        getClient().getRestaurantOrder("yNLaCDx9fCPtGpb3savL1vLCBaYzMMtdX3Kl2El5SkFwobf3bJd6FSzUBhdo", "current", page).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    currentOrderDataList.addAll(response.body().getData().getData());
                    currentOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
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
}

