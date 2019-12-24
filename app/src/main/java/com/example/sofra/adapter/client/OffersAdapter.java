package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.helper.HelperMethod;
import com.example.sofra.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<OffersData> offersDataList = new ArrayList<>();

    public OffersAdapter(Activity activity, List<OffersData> offersDataList) {
        this.activity = (BaseActivity) activity;
        this.offersDataList = offersDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_offers_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemOffersListTvOffer.setText(offersDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemOffersListCiImage,offersDataList.get(position).getPhotoUrl(),activity);


    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @OnClick(R.id.item_offers_list_bt_details)
    public void onViewClicked() {
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_offers_list_tv_offer)
        TextView itemOffersListTvOffer;
        @BindView(R.id.item_offers_list_bt_details)
        Button itemOffersListBtDetails;
        @BindView(R.id.item_offers_list_ci_image)
        CircleImageView itemOffersListCiImage;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
