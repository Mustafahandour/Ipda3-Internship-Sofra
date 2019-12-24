package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.register.ClientCategoryData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details.FoodMenuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.data.local.SharedPreferencesManger.SaveData;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class ClientHomeCategoryAdapter extends RecyclerView.Adapter<ClientHomeCategoryAdapter.ViewHolder> {

    private final FoodMenuFragment foodMenuFragment;
    public BaseActivity activity;


    private List<ClientCategoryData> clientCategoryDataList = new ArrayList<>();

    public ClientHomeCategoryAdapter(Activity activity, List<ClientCategoryData> clientCategoryDataList, FoodMenuFragment foodMenuFragment) {
        this.activity = (BaseActivity) activity;
        this.clientCategoryDataList = clientCategoryDataList;
        this.foodMenuFragment = foodMenuFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_category_restaurant_details_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemCategoryRestaurantDetailsListTvCategory.setText(clientCategoryDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemCategoryRestaurantDetailsListCiImage, clientCategoryDataList.get(position).getPhotoUrl(), activity);
    }

    private void setAction(ViewHolder holder, int position) {
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodMenuFragment.clientCategoryId = clientCategoryDataList.get(position).getId();

                foodMenuFragment.getItems(1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clientCategoryDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_category_restaurant_details_list_ci_image)
        CircleImageView itemCategoryRestaurantDetailsListCiImage;
        @BindView(R.id.item_category_restaurant_details_list_ci_ll)
        LinearLayout itemCategoryRestaurantDetailsListCiLl;
        @BindView(R.id.item_category_restaurant_details_list_tv_category)
        TextView itemCategoryRestaurantDetailsListTvCategory;


        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
