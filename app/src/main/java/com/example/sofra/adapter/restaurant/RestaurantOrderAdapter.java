package com.example.sofra.adapter.restaurant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.order.OrderData;
import com.example.sofra.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantOrderAdapter extends RecyclerView.Adapter<RestaurantOrderAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<OrderData> orderDataList = new ArrayList<>();


    public RestaurantOrderAdapter(Activity activity, List<OrderData> orderDataList) {
        this.activity = (BaseActivity) activity;
        this.orderDataList = orderDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_order_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {

        for (int i = 0; i < orderDataList.get(position).getItems().size(); i++) {
            holder.itemRestaurantOrderListTvRestName.setText(orderDataList.get(position).getItems().get(i).getName());
            onLoadImageFromUrl(holder.itemRestaurantOrderListCiImage, orderDataList.get(position).getItems().get(i).getPhotoUrl(), activity);

        }
        holder.itemRestaurantOrderListTvOrderNum.setText(activity.getString(R.string.order_num) + " " + orderDataList.get(position).getId());

        holder.itemRestaurantOrderListTvTotalCost.setText(activity.getString(R.string.t_cost) + " " + orderDataList.get(position).getTotal());
        holder.itemRestaurantOrderListTvAddress.setText(activity.getString(R.string.address) + " " + orderDataList.get(position).getAddress());

        if (orderDataList.get(position).getState().equals("pending")) {
            holder.itemRestaurantOrderListBtOrderCancel.setText(activity.getString(R.string.cancel));
            holder.itemRestaurantOrderListBtOrderCancel.setBackgroundResource(R.drawable.shap_circl_rect_deep_blue);
            holder.itemRestaurantOrderListBtOrderAccept.setText(activity.getString(R.string.accept));
            holder.itemRestaurantOrderListBtOrderAccept.setBackgroundResource(R.drawable.shap_circl_rect_green);
            holder.itemRestaurantOrderListBtOrderCall.setText(activity.getString(R.string.call));
            holder.itemRestaurantOrderListBtOrderCall.setBackgroundResource(R.drawable.shap_circl_rect_red);

        } else if (orderDataList.get(position).getState().equals("accepted")) {
            holder.itemRestaurantOrderListBtOrderAccept.setVisibility(View.GONE);
            holder.itemRestaurantOrderListBtOrderCall.setText(activity.getString(R.string.delivery_confirm));
            holder.itemRestaurantOrderListBtOrderCall.setBackgroundResource(R.drawable.shap_circl_rect_green);
            holder.itemRestaurantOrderListBtOrderCancel.setText(activity.getString(R.string.cancel));
            holder.itemRestaurantOrderListBtOrderCancel.setBackgroundResource(R.drawable.shap_circl_rect_deep_blue);

        } else if (orderDataList.get(position).getState().equals("delivered")) {
            holder.itemRestaurantOrderListBtOrderCall.setVisibility(View.GONE);
            holder.itemRestaurantOrderListBtOrderCancel.setVisibility(View.GONE);
            holder.itemRestaurantOrderListBtOrderAccept.setText(activity.getString(R.string.accept));
            holder.itemRestaurantOrderListBtOrderAccept.setBackgroundResource(R.drawable.shap_circl_rect_green);

        } else {
            holder.itemRestaurantOrderListBtOrderCall.setVisibility(View.GONE);
            holder.itemRestaurantOrderListBtOrderCancel.setVisibility(View.GONE);
            holder.itemRestaurantOrderListBtOrderAccept.setText(activity.getString(R.string.declined));
            holder.itemRestaurantOrderListBtOrderAccept.setBackgroundResource(R.drawable.shap_circl_rect_red);
        }

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_order_list_tv_rest_name)
        TextView itemRestaurantOrderListTvRestName;
        @BindView(R.id.item_restaurant_order_list_tv_order_num)
        TextView itemRestaurantOrderListTvOrderNum;
        @BindView(R.id.item_restaurant_order_list_tv_total_cost)
        TextView itemRestaurantOrderListTvTotalCost;
        @BindView(R.id.item_restaurant_order_list_tv_address)
        TextView itemRestaurantOrderListTvAddress;
        @BindView(R.id.item_restaurant_order_list_ci_image)
        CircleImageView itemRestaurantOrderListCiImage;
        @BindView(R.id.item_restaurant_order_list_bt_order_call)
        Button itemRestaurantOrderListBtOrderCall;
        @BindView(R.id.item_restaurant_order_list_bt_order_accept)
        Button itemRestaurantOrderListBtOrderAccept;
        @BindView(R.id.item_restaurant_order_list_bt_order_Cancel)
        Button itemRestaurantOrderListBtOrderCancel;


        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
