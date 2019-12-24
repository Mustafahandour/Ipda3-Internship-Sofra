package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class ClientOrderAdapter extends RecyclerView.Adapter<ClientOrderAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<OrderData> orderDataList = new ArrayList<>();


    public ClientOrderAdapter(Activity activity, List<OrderData> orderDataList) {
        this.activity = (BaseActivity) activity;
        this.orderDataList = orderDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_client_order_list, parent, false);
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
            holder.itemCurrentOrderListTvRestName.setText(orderDataList.get(position).getItems().get(i).getName());
            onLoadImageFromUrl(holder.itemCurrentOrderListCiImage, orderDataList.get(position).getItems().get(i).getPhotoUrl(), activity);

        }
        holder.itemCurrentOrderListTvOrderNum.setText(activity.getString(R.string.order_num) + " " + orderDataList.get(position).getId());

        holder.itemCurrentOrderListTvTotalCost.setText(activity.getString(R.string.t_cost)  + " " + orderDataList.get(position).getTotal());
        holder.itemCurrentOrderListTvAddress.setText(activity.getString(R.string.address)  + " " + orderDataList.get(position).getAddress());

        if (orderDataList.get(position).getState().equals("pending")) {
            holder.itemCurrentOrderListBtOrderConfirm.setText(activity.getString(R.string.cancel));
            holder.itemCurrentOrderListBtOrderConfirm.setBackgroundResource(R.drawable.shap_circl_rect_deep_blue);

        }else if (orderDataList.get(position).getState().equals("accepted") ){
            holder.itemCurrentOrderListBtOrderConfirm.setText(activity.getString(R.string.comfirm_delivery));
            holder.itemCurrentOrderListBtOrderConfirm.setBackgroundResource(R.drawable.shap_circl_rect_green);

        }else if (orderDataList.get(position).getState().equals("delivered")){
            holder.itemCurrentOrderListBtOrderConfirm.setText(activity.getString(R.string.deliveried));
            holder.itemCurrentOrderListBtOrderConfirm.setBackgroundResource(R.drawable.shap_circl_rect_green);

        }else{
            holder.itemCurrentOrderListBtOrderConfirm.setText(activity.getString(R.string.declined));
            holder.itemCurrentOrderListBtOrderConfirm.setBackgroundResource(R.drawable.shap_circl_rect_red);
        }

    }

    private void setAction(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_current_order_list_tv_rest_name)
        TextView itemCurrentOrderListTvRestName;
        @BindView(R.id.item_current_order_list_tv_order_num)
        TextView itemCurrentOrderListTvOrderNum;
        @BindView(R.id.item_current_order_list_tv_total_cost)
        TextView itemCurrentOrderListTvTotalCost;
        @BindView(R.id.item_current_order_list_tv_address)
        TextView itemCurrentOrderListTvAddress;
        @BindView(R.id.item_current_order_list_ll_info)
        LinearLayout itemCurrentOrderListLlInfo;
        @BindView(R.id.item_current_order_list_ci_image)
        CircleImageView itemCurrentOrderListCiImage;
        @BindView(R.id.item_current_order_list_rl_ci)
        RelativeLayout itemCurrentOrderListRlCi;
        @BindView(R.id.item_current_order_list_bt_order_confirm)
        Button itemCurrentOrderListBtOrderConfirm;

        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
