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
import com.example.sofra.data.model.category.Category;
import com.example.sofra.data.model.itemList.ItemList;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.restaurant.RestaurantHomeFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
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

public class RestaurantItemAdapter extends RecyclerView.Adapter<RestaurantItemAdapter.ViewHolder> {

    public BaseActivity activity;
    CircleImageView dialogAddItemCiImage;
    EditText dialogAddItemEtItemName;
    EditText dialogAddItemEtItemDes;
    EditText dialogAddItemEtItemPrice;
    EditText dialogAddItemEtItemOffer;
    Button dialogAddItemBtAdd;
    TextView itemDialogText;

    private List<ItemData> itemDataList = new ArrayList<>();
    private RestaurantHomeFragment restaurantHomeFragment;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;

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
        holder.itemsRestaurantTvItemCost.setText(activity.getString(R.string.cost) + "\n" + itemDataList.get(position).getPrice());

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
        getClient().deleteItem(LoadData(activity, "Restaurant_ApiToken"), String.valueOf(itemDataList.get(i).getCategoryId())).enqueue(new Callback<Category>() {
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
        dialog.setContentView(R.layout.dialog_add_item);
        CircleImageView dialogAddItemCiImage = dialog.findViewById(R.id.dialog_add_item_ci_image);
        onLoadImageFromUrl(dialogAddItemCiImage, itemDataList.get(i).getPhotoUrl(), activity);
        itemDialogText = dialog.findViewById(R.id.item_dialog_text);
        itemDialogText.setText(R.string.edit_item);
        dialogAddItemEtItemName = dialog.findViewById(R.id.dialog_add_item_et_item_name);
        dialogAddItemEtItemDes = dialog.findViewById(R.id.dialog_add_item_et_item_des);
        dialogAddItemEtItemPrice = dialog.findViewById(R.id.dialog_add_item_et_item_price);
        dialogAddItemEtItemOffer = dialog.findViewById(R.id.dialog_add_item_et_item_offer);
        dialogAddItemCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(activity, 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogAddItemCiImage, path, activity);

                    }
                });
            }
        });
        Button dialogAddItemBtAdd = dialog.findViewById(R.id.dialog_add_item_bt_add);

        dialogAddItemBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                editItem(i);

            }
        });


        dialog.show();
    }

    private void editItem(int i) {
        String name = dialogAddItemEtItemName.getText().toString().trim();
        String des = dialogAddItemEtItemDes.getText().toString().trim();
        String price = dialogAddItemEtItemPrice.getText().toString().trim();
        String offer = dialogAddItemEtItemOffer.getText().toString().trim();
        getClient().getItemEdit(convertToRequestBody(LoadData(activity, "Restaurant_ApiToken")), convertToRequestBody(String.valueOf(itemDataList.get(i).getId())),
                convertToRequestBody(name), convertToRequestBody(des), convertToRequestBody(price), convertToRequestBody(offer), convertFileToMultipart(path, "photo")).enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(activity, response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
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
