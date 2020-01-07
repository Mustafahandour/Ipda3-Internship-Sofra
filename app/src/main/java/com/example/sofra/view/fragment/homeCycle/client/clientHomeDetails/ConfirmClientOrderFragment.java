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
import com.example.sofra.data.model.order.Order;
import com.example.sofra.view.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;

public class ConfirmClientOrderFragment extends BaseFragment {

    public double tCost;
    @BindView(R.id.fragment_confirm_client_order_et_note)
    EditText fragmentConfirmClientOrderEtNote;
    @BindView(R.id.fragment_confirm_client_order_tv_address)
    TextView fragmentConfirmClientOrderTvAddress;
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
    private int id;

    public ConfirmClientOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_client_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragmentConfirmClientOrderTvTotal.setText(getString(R.string.total_cost) + "   "+(int) tCost);
        fragmentConfirmClientOrderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_confirm_client_order_rb_pay_cash:
                        id = 1;

                        break;
                    case R.id.fragment_confirm_client_order_tv_total:
                        id = 2;
                        break;
                }

            }
        });

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

    @OnClick(R.id.fragment_confirm_client_order_bt_confirm)
    public void onViewClicked() {
    //    getOrderDone();
    }

//    private void getOrderDone() {
//        getClient().getOrderDone().enqueue(new Callback<Order>() {
//            @Override
//            public void onResponse(Call<Order> call, Response<Order> response) {
//                try {
//                    if (response.body().getStatus() == 1) {
//                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
//
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Order> call, Throwable t) {
//
//            }
//        });
//    }
}

