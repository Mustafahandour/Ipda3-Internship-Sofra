package com.example.sofra.view.fragment.homeCycle.general;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.general.NotificationAdapter;
import com.example.sofra.data.model.notification.Notification;
import com.example.sofra.data.model.notification.NotificationData;
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

public class NotificationFragment extends BaseFragment {

    @BindView(R.id.notification_fragment_rv_notification)
    RecyclerView notificationFragmentRvNotification;
    private Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private OnEndLess onEndLess;
    private int maxPage;
    List<NotificationData> notificationDataList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFragment();
        getData();
        return view;
    }

    private void getData() {
         linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationFragmentRvNotification.setLayoutManager(linearLayoutManager);

         onEndLess = new OnEndLess(linearLayoutManager,1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page<= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        //getNotification(current_page);
                        getGeneralNotification(current_page);
                    }else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }

            }
        };
         notificationFragmentRvNotification.addOnScrollListener(onEndLess);
         notificationAdapter = new NotificationAdapter(getActivity(),notificationDataList);
        notificationFragmentRvNotification.setAdapter(notificationAdapter);
        //getNotification(1);
        getGeneralNotification(1); }
//
//    private void getNotification(int page) {
//        if (LoadData(getActivity(),USER_TYPE).equals(USER_TYPE_RESTAURANT)) {
//            getGeneralNotification(getClient().getRestaurantNotification("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx"),page);
//        }else {
//            getGeneralNotification(getClient().getClientNotification("K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh"),page);
//
//        }
//    }
//
//    private void getGeneralNotification(Call<Notification> call,int page) {
//        call.enqueue(new Callback<Notification>() {
//            @Override
//            public void onResponse(Call<Notification> call, Response<Notification> response) {
//                if (response.body().getStatus() == 1) {
//                    maxPage = response.body().getData().getLastPage();
//                    notificationDataList.addAll(response.body().getData().getData());
//                    notificationAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Notification> call, Throwable t) {
//
//            }
//        });
//    }
    private void getGeneralNotification(int page) {
        getClient().getClientNotification("K1X6AzRlJFeVbGnHwGYsdCu0ETP1BqYC7DpMTZ3zLvKgU5feHMvsEEnKTpzh").enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    notificationDataList.addAll(response.body().getData().getData());
                    notificationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

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

