package com.example.sofra.adapter.restaurant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.restaurant.RestaurantHomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class RestaurantItemAdapter extends RecyclerView.Adapter<RestaurantItemAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<ItemData> itemDataList = new ArrayList<>();
    private  RestaurantHomeFragment restaurantHomeFragment;

    public RestaurantItemAdapter(Activity activity, List<ItemData> itemDataList) {
        this.activity = (BaseActivity) activity;
        this.itemDataList = itemDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.items_restaurant_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemsRestaurantTvItemName.setText(itemDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemsRestaurantIvImage, itemDataList.get(position).getPhotoUrl(), activity);
        holder.itemsRestaurantTvItemDes.setText(itemDataList.get(position).getDescription());
        holder.itemsRestaurantTvItemCost.setText(activity.getString(R.string.cost)+"\n"+itemDataList.get(position).getPrice());

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    @OnClick({R.id.items_restaurant_ib_delete, R.id.items_restaurant_ib_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.items_restaurant_ib_delete:
                deleteItem();
                break;
            case R.id.items_restaurant_ib_edit:
                break;
        }
    }

    private void deleteItem() {

    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.items_restaurant_ib_delete)
        ImageButton itemsRestaurantIbDelete;
        @BindView(R.id.items_restaurant_ib_edit)
        ImageButton itemsRestaurantIbEdit;
        @BindView(R.id.items_restaurant_iv_image)
        ImageView itemsRestaurantIvImage;
        @BindView(R.id.items_restaurant_tv_item_name)
        TextView itemsRestaurantTvItemName;
        @BindView(R.id.items_restaurant_tv_item_des)
        TextView itemsRestaurantTvItemDes;
        @BindView(R.id.items_restaurant_tv_item_cost)
        TextView itemsRestaurantTvItemCost;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
