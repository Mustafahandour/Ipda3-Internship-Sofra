package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.ClientHomeCategoryAdapter;
import com.example.sofra.adapter.client.ClientHomeItemsAdapter;
import com.example.sofra.data.model.itemList.ItemList;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.data.model.register.ClientCategory;
import com.example.sofra.data.model.register.ClientCategoryData;
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

import static com.example.sofra.data.api.ApiClient.getClient;

public class FoodMenuFragment extends BaseFragment {

    @BindView(R.id.fragment_menu_food_rv_category)
    RecyclerView fragmentMenuFoodRvCategory;
    @BindView(R.id.fragment_menu_food_rv_items)
    RecyclerView fragmentMenuFoodRvItems;

    public ClientCategoryData clientCategoryData;
    public Integer id;
    private Unbinder unbinder;
    private int maxPage;
    private OnEndLess onEndLess;
    List<ItemData> itemDataList = new ArrayList<>();
    List<ClientCategoryData> categoryDataList = new ArrayList<>();
    private ClientHomeCategoryAdapter restaurantDetailsCategoryAdapter;
    private ClientHomeItemsAdapter restaurantDetailsItemsAdapter;
    private LinearLayoutManager linearLayoutManagerItems, linearLayoutManagerCategory;
    public int clientCategoryId;

    public FoodMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_food, container, false);
        unbinder = ButterKnife.bind(this, view);

        getCategoryData();
        getItemData();

        return view;
    }

    private void getCategoryData() {

        getClient().getCategory(String.valueOf(id)).enqueue(new Callback<ClientCategory>() {
            @Override
            public void onResponse(Call<ClientCategory> call, Response<ClientCategory> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        categoryDataList.addAll(response.body().getData());
                        linearLayoutManagerCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        fragmentMenuFoodRvCategory.setLayoutManager(linearLayoutManagerCategory);
                        restaurantDetailsCategoryAdapter = new ClientHomeCategoryAdapter(getActivity(), categoryDataList , FoodMenuFragment.this);
                        fragmentMenuFoodRvCategory.setAdapter(restaurantDetailsCategoryAdapter);
                        restaurantDetailsCategoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ClientCategory> call, Throwable t) {

            }
        });

    }

    private void getItemData() {
        linearLayoutManagerItems = new LinearLayoutManager(getActivity());

        fragmentMenuFoodRvItems.setLayoutManager(linearLayoutManagerItems);

        onEndLess = new OnEndLess(linearLayoutManagerItems, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getItems(current_page);
                    }
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentMenuFoodRvItems.addOnScrollListener(onEndLess);
        restaurantDetailsItemsAdapter = new ClientHomeItemsAdapter(getActivity(), itemDataList);
        fragmentMenuFoodRvItems.setAdapter(restaurantDetailsItemsAdapter);
        getItems(1);
    }

    public void getItems(int page) {
        getClient().getItemList(id, clientCategoryId, page).enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        if (page == 1) {

                        }
                        maxPage = response.body().getData().getLastPage();
                        itemDataList.addAll(response.body().getData().getData());

                        restaurantDetailsItemsAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {

                }
            }


            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {

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

