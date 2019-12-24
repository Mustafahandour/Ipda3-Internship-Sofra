package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.ClientHomeAdapter;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.data.model.restaurantList.RestaurantList;
import com.example.sofra.data.model.restaurantList.RestaurantListData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.helper.GeneralRequest.getSpinnerData;

public class ClientHomeFragment extends BaseFragment {


    @BindView(R.id.home_fragment_sp_city)
    Spinner homeFragmentSpCity;
    @BindView(R.id.home_fragment_et_restaurant)
    EditText homeFragmentEtPickRestaurant;
    @BindView(R.id.home_fragment_rv_menu)
    RecyclerView homeFragmentRvMenu;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.home_fragment_ib_search)
    ImageButton homeFragmentIbSearch;
    private ClientHomeAdapter restaurantsListAdapter;
    List<RestaurantListData> showRestaurantDataList = new ArrayList<>();

    private int maxPAge;
    private OnEndLess onEndLess;
    private Unbinder unbinder;

    private boolean filter = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        initFragment();
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        homeFragmentRvMenu.setLayoutManager(linearLayoutManager);
        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(getActivity());
        getSpinnerData(homeFragmentSpCity, customSpinnerAdapter, getString(R.string.city), getClient().getCity());


        getClientHomeData();


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getClientHomeData() {

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPAge) {
                    if (maxPAge != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;




                            getRestaurantList(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }


        };
        homeFragmentRvMenu.addOnScrollListener(onEndLess);
        restaurantsListAdapter = new ClientHomeAdapter(getActivity(), showRestaurantDataList);
        homeFragmentRvMenu.setAdapter(restaurantsListAdapter);
        getRestaurantList(1);


    }

    private void getFilter() {
        filter = true;
        onEndLess.previousTotal = 0;
        onEndLess.previous_page = 1;
        onEndLess.current_page = 1;
        String key = homeFragmentEtPickRestaurant.getText().toString();
        int city = homeFragmentSpCity.getSelectedItemPosition();
        getClient().getRestaurantFilter(key, city).enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(Call<RestaurantList> call, Response<RestaurantList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        showRestaurantDataList.addAll(response.body().getData().getData());
                        restaurantsListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantList> call, Throwable t) {

            }
        });
    }

    private void getRestaurantList(int page) {
        getClient().getRestaurantList(page).enqueue(new Callback<RestaurantList>() {
            @Override
            public void onResponse(Call<RestaurantList> call, Response<RestaurantList> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        maxPAge = response.body().getData().getLastPage();
                        showRestaurantDataList.addAll(response.body().getData().getData());
                        restaurantsListAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<RestaurantList> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBack() {
        getActivity().finish();
    }

    @OnClick(R.id.home_fragment_ib_search)
    public void onViewClicked() {
        getFilter();

    }
}