package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.local.room.Item;
import com.example.sofra.data.local.room.RoomDao;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.client.CartFragment;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.ConfirmClientOrderFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.data.local.room.RoomManger.getInstance;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public BaseActivity activity;
CartFragment cartFragment;
    private List<Item> items = new ArrayList<>();
    private RoomDao roomDao;
    private int quantity;

    public CartAdapter(Activity activity, List<Item> items) {
        this.activity = (BaseActivity) activity;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_cart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void setData(ViewHolder holder, int position) {
        holder.itemCartListTvName.setText(items.get(position).getItemName());
        holder.itemCartListTvCost.setText(String.valueOf(items.get(position).getCost()));
        holder.itemCartListTvQuantity.setText(String.valueOf(items.get(position).getQuantity()));
        HelperMethod.onLoadImageFromUrl(holder.itemCartListIvImage, items.get(position).getImage(), activity);

    }

    private void setAction(ViewHolder holder, int position) {
        holder.itemCartListBtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomDao = getInstance(activity).roomDao();
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        roomDao.delete(items.get(position));
                        items.remove(position);
                        cartFragment.updateUi(items);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notifyDataSetChanged();

                            }
                        });
                    }
                });
            }
        });


    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_cart_list_iv_image)
        ImageView itemCartListIvImage;
        @BindView(R.id.item_cart_list_tv_name)
        TextView itemCartListTvName;
        @BindView(R.id.item_cart_list_tv_cost)
        TextView itemCartListTvCost;
        @BindView(R.id.item_cart_list_tv_increase)
        TextView itemCartListTvIncrease;
        @BindView(R.id.item_cart_list_tv_quantity)
        TextView itemCartListTvQuantity;
        @BindView(R.id.item_cart_list_tv_decrease)
        TextView itemCartListTvDecrease;
        @BindView(R.id.item_cart_list_bt_cancel)
        Button itemCartListBtCancel;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
