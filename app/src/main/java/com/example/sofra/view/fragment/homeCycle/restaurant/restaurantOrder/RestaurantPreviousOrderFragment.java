package com.example.sofra.view.fragment.homeCycle.restaurant.restaurantOrder;

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

public class RestaurantPreviousOrderFragment extends BaseFragment {

    @BindView(R.id.order_current_fragment_rv)
    RecyclerView orderCurrentFragmentRv;
    private Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage;
    List<OrderData> previousOrderDataList= new ArrayList<>();
    private RestaurantOrderAdapter restaurantOrderAdapter;

    public RestaurantPreviousOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_client, container, false);
        unbinder = ButterKnife.bind(this, view);

                getRestaurantData();


        return view;
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
                        getRestaurantPreviousOrder(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }
            }
        };
        orderCurrentFragmentRv.addOnScrollListener(onEndLess);
        restaurantOrderAdapter = new RestaurantOrderAdapter(getActivity(), previousOrderDataList);
        orderCurrentFragmentRv.setAdapter(restaurantOrderAdapter);
        getRestaurantPreviousOrder(1);
    }

    private void getRestaurantPreviousOrder(int page) {
        getClient().getRestaurantOrder(LoadData(getActivity(),"Restaurant_ApiToken"), "completed", page).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    previousOrderDataList.addAll(response.body().getData().getData());
                    restaurantOrderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
        @Override
        public void onDestroyView () {
            super.onDestroyView();
            unbinder.unbind();
        }
        @Override
        public void onBack () {
            super.onBack();
        }
    }

