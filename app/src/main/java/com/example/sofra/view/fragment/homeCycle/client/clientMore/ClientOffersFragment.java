package com.example.sofra.view.fragment.homeCycle.client.clientMore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.OffersAdapter;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.offers.OffersData;
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

public class ClientOffersFragment extends BaseFragment {

    @BindView(R.id.fragment_offers_rv_offer)
    RecyclerView fragmentOffersRvOffer;
    private Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private List<OffersData> offersDataList = new ArrayList<>();
    private int maxPage;
    private OnEndLess onEndLess;
    private OffersAdapter offersAdapter;

    public ClientOffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        unbinder = ButterKnife.bind(this, view);


        getData();

        return view;
    }

    private void getData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentOffersRvOffer.setLayoutManager(linearLayoutManager);
         onEndLess = new OnEndLess(linearLayoutManager,1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page<= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getOffer(current_page);
                    }else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                }

            }
        };
         fragmentOffersRvOffer.addOnScrollListener(onEndLess);
         offersAdapter = new OffersAdapter(getActivity(),offersDataList);
         fragmentOffersRvOffer.setAdapter(offersAdapter);
         getOffer(1);

    }

    private void getOffer(int page) {
        getClient().getOffers(1, page).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        offersDataList.addAll(response.body().getData().getData());
                        offersAdapter.notifyDataSetChanged();

                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

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

