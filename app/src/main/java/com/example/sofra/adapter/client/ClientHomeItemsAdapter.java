package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.AddClientOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.replace;

public class ClientHomeItemsAdapter extends RecyclerView.Adapter<ClientHomeItemsAdapter.ViewHolder> {

    public BaseActivity activity;


    private List<ItemData> itemDataList = new ArrayList<>();

    public ClientHomeItemsAdapter(Activity activity, List<ItemData> itemDataList) {
        this.activity = (BaseActivity) activity;
        this.itemDataList = itemDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.items_restaurant_details_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        onLoadImageFromUrl(holder.itemsRestaurantDetailsListIvImage, itemDataList.get(position).getPhotoUrl(), activity);
        holder.itemsRestaurantDetailsListTvName.setText(itemDataList.get(position).getName());
        holder.itemsRestaurantDetailsListTvItemDescription.setText(itemDataList.get(position).getDescription());
      try{
          if (itemDataList.get(position).getOfferPrice() != null) {

              holder.itemsRestaurantDetailsListTvCost.setText(activity.getString(R.string.cost)+" "
                      +itemDataList.get(position).getPrice()+" "+itemDataList.get(position).getOfferPrice());
          }
          holder.itemsRestaurantDetailsListTvCost.setText(activity.getString(R.string.cost)+" "
                  +itemDataList.get(position).getPrice());
      }catch (Exception e){

      }

    }

    private void setAction(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClientOrderFragment addClientOrderFragment = new AddClientOrderFragment();
                addClientOrderFragment.itemData = itemDataList.get(position);
                replace(addClientOrderFragment,activity.getSupportFragmentManager(),R.id.nav_host_fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.items_restaurant_details_list_iv_image)
        ImageView itemsRestaurantDetailsListIvImage;
        @BindView(R.id.items_restaurant_details_list_tv_name)
        TextView itemsRestaurantDetailsListTvName;
        @BindView(R.id.items_restaurant_details_list_tv_item_description)
        TextView itemsRestaurantDetailsListTvItemDescription;
        @BindView(R.id.items_restaurant_details_list_tv_cost)
        TextView itemsRestaurantDetailsListTvCost;

        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
