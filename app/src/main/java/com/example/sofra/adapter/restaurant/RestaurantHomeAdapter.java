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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.category.Category;
import com.example.sofra.data.model.category.CategoryData;
import com.example.sofra.view.activity.BaseActivity;
import com.example.sofra.view.fragment.homeCycle.restaurant.RestaurantItemsFragment;
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
import static com.example.sofra.helper.HelperMethod.replace;

public class RestaurantHomeAdapter extends RecyclerView.Adapter<RestaurantHomeAdapter.ViewHolder> {

    public BaseActivity activity;

    CircleImageView dialogEditCategoryCiImage;

    EditText dialogEditCategoryEtCategoryName;


    private List<CategoryData> categoryDataList = new ArrayList<>();
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;

    public RestaurantHomeAdapter(Activity activity, List<CategoryData> categoryDataList) {
        this.activity = (BaseActivity) activity;
        this.categoryDataList = categoryDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_category_restaurant_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemCategoryRestaurantTvCategory.setText(categoryDataList.get(position).getName());
        onLoadImageFromUrl(holder.itemCategoryRestaurantIvImage, categoryDataList.get(position).getPhotoUrl(), activity);

    }

    private void setAction(ViewHolder holder, int position) {
        holder.itemCategoryRestaurantRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RestaurantItemsFragment restaurantCategoryDetailaFragment = new RestaurantItemsFragment();
                restaurantCategoryDetailaFragment.categoryId = categoryDataList.get(position).getId();
                replace(restaurantCategoryDetailaFragment, activity.getSupportFragmentManager(), R.id.nav_host_fragment);

            }
        });
        holder.itemCategoryRestaurantIbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(position);
                notifyDataSetChanged();

            }
        });
        holder.itemCategoryRestaurantIbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);

            }
        });

    }

    private void deleteCategory(int i) {
        getClient().deleteCategory(LoadData(activity, "Restaurant_ApiToken"), String.valueOf(categoryDataList.get(i).getId())).enqueue(new Callback<Category>() {
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
        dialog.setContentView(R.layout.dialog_edit_category);


        dialogEditCategoryEtCategoryName = dialog.findViewById(R.id.dialog_edit_category_et_category_name);
        // set the custom dialog components  image clicked
        dialogEditCategoryCiImage = dialog.findViewById(R.id.dialog_edit_category_ci_image);
        onLoadImageFromUrl(dialogEditCategoryCiImage, categoryDataList.get(i).getPhotoUrl(), activity);
        dialogEditCategoryCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(activity, 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogEditCategoryCiImage, path, activity);
                    }
                });

            }
        });


        // if button is clicked
        Button dialogEditCategoryBtAdd = dialog.findViewById(R.id.dialog_edit_category_bt_add);
        dialogEditCategoryBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                editCategory(i);
                notifyDataSetChanged();

            }
        });
        dialog.show();
    }

    private void editCategory(int i) {
        String name = dialogEditCategoryEtCategoryName.getText().toString().trim();
        getClient().editCategory(convertToRequestBody(name), convertFileToMultipart(path, "photo"),
                convertToRequestBody(LoadData(activity, "Restaurant_ApiToken")),
                convertToRequestBody(String.valueOf(categoryDataList.get(i).getId()))).enqueue(new Callback<Category>() {
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


    @Override
    public int getItemCount() {
        return categoryDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_category_restaurant_ib_delete)
        ImageButton itemCategoryRestaurantIbDelete;
        @BindView(R.id.item_category_restaurant_ib_edit)
        ImageButton itemCategoryRestaurantIbEdit;
        @BindView(R.id.item_category_restaurant_iv_image)
        ImageView itemCategoryRestaurantIvImage;
        @BindView(R.id.item_category_restaurant_tv_category)
        TextView itemCategoryRestaurantTvCategory;
        @BindView(R.id.item_category_restaurant_rl)
        RelativeLayout itemCategoryRestaurantRl;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
