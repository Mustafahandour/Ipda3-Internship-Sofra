package com.example.sofra.adapter.restaurant;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.DateTxt;
import com.example.sofra.data.model.category.Category;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.restaurant.RestaurantHomeFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;
import static com.example.sofra.data.local.SharedPreferencesManger.LoadData;
import static com.example.sofra.helper.HelperMethod.convertFileToMultipart;
import static com.example.sofra.helper.HelperMethod.convertToRequestBody;
import static com.example.sofra.helper.HelperMethod.onLoadImageFromUrl;
import static com.example.sofra.helper.HelperMethod.openGallery;
import static com.example.sofra.helper.HelperMethod.showCalender;

public class RestaurantOffersAdapter extends RecyclerView.Adapter<RestaurantOffersAdapter.ViewHolder> {

    public BaseActivity activity;
    CircleImageView dialogAddOfferCiImage;
    EditText dialogAddOfferEtOfferName;
    EditText dialogAddOfferEtOfferDes;
    EditText dialogAddOfferEtPrice;
    EditText dialogAddOfferEtOfferPrice;
    Button dialogAddOfferBtAdd;
    TextView dialogAddOfferTvDateFrom;
    TextView dialogAddOfferTvDateTo;
    TextView dialogOfferText;


    private List<OffersData> offersDataList = new ArrayList<>();
    private RestaurantHomeFragment restaurantHomeFragment;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;

    public RestaurantOffersAdapter(Activity activity, List<OffersData> offersDataList) {
        this.activity = (BaseActivity) activity;
        this.offersDataList = offersDataList;
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
        holder.itemsRestaurantTvItemName.setText(offersDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemsRestaurantIvImage, offersDataList.get(position).getPhotoUrl(), activity);
        holder.itemsRestaurantTvItemDes.setText(offersDataList.get(position).getDescription());
        holder.itemsRestaurantTvItemCost.setVisibility(View.GONE);

    }

    private void setAction(ViewHolder holder, int position) {
        holder.itemsRestaurantIbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
                notifyDataSetChanged();
            }
        });

        holder.itemsRestaurantIbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
                notifyDataSetChanged();

            }
        });

    }

    private void deleteItem(int i) {
        getClient().deleteOffer(LoadData(activity, "Restaurant_ApiToken"), String.valueOf(offersDataList.get(i).getId())).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    private void openDialog(int i) {

        // custom dialog
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_add_offers);
        CircleImageView dialogAddOfferCiImage = dialog.findViewById(R.id.dialog_add_offer_ci_image);
        dialogOfferText = dialog.findViewById(R.id.dialog_offer_text);
        dialogOfferText.setText(R.string.edit_offers);
        dialogAddOfferCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(activity, 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogAddOfferCiImage, path, activity);

                    }
                });
            }
        });

        dialogAddOfferTvDateFrom = dialog.findViewById(R.id.dialog_add_offer_tv_date_from);
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        DateTxt dateTxt = new DateTxt(day, month, year, day + "-" + month + "-" + year);
        dialogAddOfferTvDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(activity, null, dialogAddOfferTvDateFrom, dateTxt);

            }
        });


        dialogAddOfferTvDateTo = dialog.findViewById(R.id.dialog_add_offer_tv_date_to);
        dialogAddOfferTvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(activity, null, dialogAddOfferTvDateTo, dateTxt);

            }
        });

        dialogAddOfferEtOfferName = dialog.findViewById(R.id.dialog_add_offer_et_offer_name);
        dialogAddOfferEtOfferDes = dialog.findViewById(R.id.dialog_add_offer_et_offer_des);
        dialogAddOfferEtPrice = dialog.findViewById(R.id.dialog_add_offer_et_price);
        dialogAddOfferEtOfferPrice = dialog.findViewById(R.id.dialog_add_offer_et_offer_price);
        Button dialogAddOfferBtAdd = dialog.findViewById(R.id.dialog_add_offer_bt_add);
        dialogAddOfferBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                editOffers(i);

            }
        });

        dialog.show();

    }

    private void editOffers(int i) {
        String name = dialogAddOfferEtOfferName.getText().toString().trim();
        String des = dialogAddOfferEtOfferDes.getText().toString().trim();
        String price = dialogAddOfferEtPrice.getText().toString().trim();
        String offerPrice = dialogAddOfferEtOfferPrice.getText().toString().trim();
        String fromDate = dialogAddOfferTvDateFrom.getText().toString().trim();
        String toDate = dialogAddOfferTvDateTo.getText().toString().trim();
        getClient().editOffers(convertToRequestBody(LoadData(activity, "Restaurant_ApiToken")), convertToRequestBody(String.valueOf(offersDataList.get(i).getId())), convertToRequestBody(name), convertToRequestBody(des),
                convertToRequestBody(price), convertToRequestBody(offerPrice), convertToRequestBody(fromDate),
                convertToRequestBody(toDate),
                convertFileToMultipart(path, "photo")).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        notifyDataSetChanged();
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return offersDataList.size();
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
