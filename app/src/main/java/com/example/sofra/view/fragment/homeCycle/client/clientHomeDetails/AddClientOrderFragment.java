package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofra.R;
import com.example.sofra.data.local.room.Item;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.fragment.BaseFragment;

import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.sofra.data.local.room.RoomManger.getInstance;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class AddClientOrderFragment extends BaseFragment {

    public ItemData itemData;
    @BindView(R.id.add_client_order_fragment_iv_image)
    ImageView addClientOrderFragmentIvImage;
    @BindView(R.id.add_client_order_fragment_tv_name)
    TextView addClientOrderFragmentTvName;
    @BindView(R.id.add_client_order_fragment_tv_item_description)
    TextView addClientOrderFragmentTvItemDescription;
    @BindView(R.id.add_client_order_fragment_tv_cost)
    TextView addClientOrderFragmentTvCost;
    @BindView(R.id.add_client_order_fragment_tv_line)
    TextView addClientOrderFragmentTvLine;
    @BindView(R.id.add_client_order_fragment_tv_note)
    TextView addClientOrderFragmentTvNote;
    @BindView(R.id.add_client_order_fragment_et_note)
    EditText addClientOrderFragmentEtNote;
    @BindView(R.id.add_client_order_fragment_tv_line2)
    TextView addClientOrderFragmentTvLine2;
    @BindView(R.id.add_client_order_fragment_tv_increase)
    TextView addClientOrderFragmentTvIncrease;
    @BindView(R.id.add_client_order_fragment_tv_qquantity)
    TextView addClientOrderFragmentTvQquantity;
    @BindView(R.id.add_client_order_fragment_tv_decrease)
    TextView addClientOrderFragmentTvDecrease;
    @BindView(R.id.add_client_order_fragment_ib_cart)
    ImageButton addClientOrderFragmentIbCart;

    private Unbinder unbinder;
    int quantity = 1;
    private RoomDao roomDao;

    public AddClientOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_client_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        getOrder();

        return view;
    }

    private void getOrder() {


        addClientOrderFragmentTvName.setText(itemData.getName());
        onLoadImageFromUrl(addClientOrderFragmentIvImage, itemData.getPhotoUrl(), getActivity());

        addClientOrderFragmentTvItemDescription.setText(itemData.getDescription());
        addClientOrderFragmentTvCost.setText(itemData.getPrice());
    }

    @Override
    public void onBack() {
        super.onBack();
    }


    @OnClick({R.id.add_client_order_fragment_tv_increase, R.id.add_client_order_fragment_tv_decrease, R.id.add_client_order_fragment_ib_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_client_order_fragment_tv_increase:
                quantity++;
                addClientOrderFragmentTvQquantity.setText(quantity);

                break;
            case R.id.add_client_order_fragment_tv_decrease:
                if (quantity > 1) {
                    quantity--;
                    addClientOrderFragmentTvQquantity.setText(quantity);


                }
                break;
            case R.id.add_client_order_fragment_ib_cart:
                 roomDao = getInstance(getActivity()).roomDao();
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Item item = new Item(itemData.getId(),itemData.getRestaurantId(),
                                itemData.getName(),addClientOrderFragmentTvQquantity.getText().toString(),itemData.getPhotoUrl(),
                                addClientOrderFragmentEtNote.getText().toString());
                        roomDao.add(item);


                    }
                });
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}

