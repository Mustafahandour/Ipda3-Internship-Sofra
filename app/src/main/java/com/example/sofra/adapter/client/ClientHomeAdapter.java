package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.restaurantList.RestaurantListData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details.RestaurantDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.replace;

public class ClientHomeAdapter extends RecyclerView.Adapter<ClientHomeAdapter.ViewHolder> {

    public BaseActivity activity;
    List<RestaurantListData> restaurantListDataList = new ArrayList<>();


    public ClientHomeAdapter(Activity activity, List<RestaurantListData> restaurantListDataList) {
        this.activity = (BaseActivity) activity;
        this.restaurantListDataList = restaurantListDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemRestaurantListTvRestaurantName.setText(restaurantListDataList.get(position).getName());
        holder.itemRestaurantListTvRestaurantState.setText(restaurantListDataList.get(position).getAvailability());
        holder.itemRestaurantListRbRate.setRating(restaurantListDataList.get(position).getRate());
        holder.itemRestaurantListTvDelCost.setText(restaurantListDataList.get(position).getDeliveryCost());
        holder.itemRestaurantListTvMimOrder.setText(restaurantListDataList.get(position).getMinimumCharger());
        onLoadImageFromUrl(holder.itemRestaurantListCiImage,restaurantListDataList.get(position).getPhotoUrl(),activity);

    }

    private void setAction(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantDetailsFragment restaurantDetailsFragment = new RestaurantDetailsFragment();
                restaurantDetailsFragment.showRestaurantData = restaurantListDataList.get(position);
                replace(restaurantDetailsFragment,activity.getSupportFragmentManager(),R.id.nav_host_fragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantListDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_restaurant_list_tv_restaurant_name)
        TextView itemRestaurantListTvRestaurantName;
        @BindView(R.id.item_restaurant_list_tv_restaurant_state)
        TextView itemRestaurantListTvRestaurantState;
        @BindView(R.id.item_restaurant_list_rb_rate)
        RatingBar itemRestaurantListRbRate;
        @BindView(R.id.item_restaurant_list_tv_mim_order)
        TextView itemRestaurantListTvMimOrder;
        @BindView(R.id.item_restaurant_list_tv_del_cost)
        TextView itemRestaurantListTvDelCost;
        @BindView(R.id.item_restaurant_list_ci_image)
        CircleImageView itemRestaurantListCiImage;

        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
