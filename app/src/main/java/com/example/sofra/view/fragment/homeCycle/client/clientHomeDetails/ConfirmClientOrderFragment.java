package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.room.Item;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.data.model.order.Order;
import com.example.sofra.data.model.order.Restaurant;
import com.example.sofra.data.model.register.RegisterDataRestaurant;
import com.example.sofra.data.model.restaurantList.RestaurantListData;
import com.example.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.CLIENT_DATA;
import static com.example.sofra.data.local.SharedPreferencesManger.loadClientData;
import static com.example.sofra.data.local.room.RoomManger.getInstance;

public class ConfirmClientOrderFragment extends BaseFragment {

    public double total = 0.0 , totalCost = 0.0;
    public List<Item> itemData;

    public List<Integer> item = new ArrayList<>();
    public List<String> note = new ArrayList<>();
    public List<Integer> quantity = new ArrayList<>();
    public String name;
    public int dCost;
    @BindView(R.id.fragment_confirm_client_order_et_note)
    EditText fragmentConfirmClientOrderEtNote;
    @BindView(R.id.fragment_confirm_client_order_et_address)
    EditText fragmentConfirmClientOrderEtAddress;
    @BindView(R.id.fragment_confirm_client_order_rb_pay_cash)
    RadioButton fragmentConfirmClientOrderRbPayCash;
    @BindView(R.id.fragment_confirm_client_order_rb_note_pay_online)
    RadioButton fragmentConfirmClientOrderRbNotePayOnline;
    @BindView(R.id.fragment_confirm_client_order_tv_total)
    TextView fragmentConfirmClientOrderTvTotal;
    @BindView(R.id.fragment_confirm_client_order_tv_delivery_cost)
    TextView fragmentConfirmClientOrderTvDeliveryCost;
    @BindView(R.id.fragment_confirm_client_order_tv_total_cost)
    TextView fragmentConfirmClientOrderTvTotalCost;
    @BindView(R.id.fragment_confirm_client_order_bt_confirm)
    Button fragmentConfirmClientOrderBtConfirm;
    @BindView(R.id.fragment_confirm_client_order_rg)
    RadioGroup fragmentConfirmClientOrderRg;
    private Unbinder unbinder;
    private int payMethodId;
    private RoomDao roomDao;

    public ConfirmClientOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_client_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        roomDao= getInstance(getActivity()).roomDao();
        setData();
        fragmentConfirmClientOrderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_confirm_client_order_rb_pay_cash:
                        payMethodId = 1;
                        break;
                    case R.id.fragment_confirm_client_order_tv_total:
                        payMethodId = 2;

                        break;
                }

            }
        });

        return view;
    }

    private void setData() {

        for (int i = 0; i < itemData.size(); i++) {

            total = total + itemData.get(i).getQuantity()* itemData.get(i).getCost();
            note.add(itemData.get(i).getNote());
            quantity.add(itemData.get(i).getQuantity());
            item.add(itemData.get(i).getItemId());

        }
        fragmentConfirmClientOrderTvTotal.setText(getString(R.string.t_cost) + " " +total);
       fragmentConfirmClientOrderTvDeliveryCost.setText(getString(R.string.delivery_cost)+"  "+ dCost);
        totalCost = total + dCost;
        fragmentConfirmClientOrderTvTotalCost.setText(getString(R.string.total_cost));
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

    @OnClick(R.id.fragment_confirm_client_order_bt_confirm)
    public void onViewClicked() {
             getOrderDone();
             delAll();
    }


    private void getOrderDone() {
        String details = fragmentConfirmClientOrderEtNote.getText().toString().trim();
        String address = fragmentConfirmClientOrderEtAddress.getText().toString().trim();
        RegisterDataRestaurant registerDataRestaurant = loadClientData(getActivity(), CLIENT_DATA);

        getClient().getOrderDone(itemData.get(0).getRestaurantId(), details,address, payMethodId,registerDataRestaurant.getUserRestaurant().getPhone(),
                registerDataRestaurant.getUserRestaurant().getName(),registerDataRestaurant.getApiToken(),item,quantity,note).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
    private void delAll() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                roomDao.deleteAll();
            }
        });
    }

}

