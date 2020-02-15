package com.example.sofra.view.fragment.homeCycle.restaurant;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.restaurant.RestaurantItemAdapter;
import com.example.sofra.data.model.itemList.ItemList;
import com.example.sofra.data.model.order.ItemData;
import com.example.sofra.view.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

public class RestaurantItemsFragment extends BaseFragment {


    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.fragment_restaurant_category_details_title)
    TextView fragmentRestaurantCategoryDetailsTitle;
    @BindView(R.id.fragment_restaurant_category_details_rv_items)
    RecyclerView fragmentRestaurantCategoryDetailsRvItems;
    @BindView(R.id.fragment_restaurant_category_details_ib_add_items)
    ImageButton fragmentRestaurantCategoryDetailsIbAddItems;
    TextView itemDialogText;

    private List<ItemData> itemDataList = new ArrayList<>();


    private Unbinder unbinder;
    private RestaurantItemAdapter restaurantItemAdapter;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;
    private EditText dialogAddItemEtItemName, dialogAddItemEtItemDes, dialogAddItemEtItemPrice, dialogAddItemEtItemOffer;
    public int categoryId;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_items, container, false);
        initFragment();
        unbinder = ButterKnife.bind(this, view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentRestaurantCategoryDetailsRvItems.setLayoutManager(linearLayoutManager);


        getRestaurantHomeData();


        return view;
    }


    private void getRestaurantHomeData() {
        getClient().getItem(LoadData(getActivity(), "Restaurant_ApiToken"), categoryId).enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (response.body().getStatus() == 1) {
                    itemDataList.addAll(response.body().getData().getData());
                    // fragmentRestaurantCategoryDetailsTitle.setText(itemDataList.get(0).getName());
                    restaurantItemAdapter = new RestaurantItemAdapter(getActivity(), itemDataList);
                    fragmentRestaurantCategoryDetailsRvItems.setAdapter(restaurantItemAdapter);
                    restaurantItemAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onBack() {
        super.onBack();
    }

    @OnClick(R.id.fragment_restaurant_category_details_ib_add_items)
    public void onViewClicked() {
        openDialog();
    }

    private void openDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_item);
        CircleImageView dialogAddItemCiImage = dialog.findViewById(R.id.dialog_add_item_ci_image);
        itemDialogText = dialog.findViewById(R.id.item_dialog_text);
        itemDialogText.setText(R.string.add_item);
        dialogAddItemEtItemName = dialog.findViewById(R.id.dialog_add_item_et_item_name);
        dialogAddItemEtItemDes = dialog.findViewById(R.id.dialog_add_item_et_item_des);
        dialogAddItemEtItemPrice = dialog.findViewById(R.id.dialog_add_item_et_item_price);
        dialogAddItemEtItemOffer = dialog.findViewById(R.id.dialog_add_item_et_item_offer);
        dialogAddItemCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(getActivity(), 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogAddItemCiImage, path, getActivity());

                    }
                });
            }
        });
        Button dialogAddItemBtAdd = dialog.findViewById(R.id.dialog_add_item_bt_add);

        dialogAddItemBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addItem();
                restaurantItemAdapter.notifyDataSetChanged();

            }
        });


        dialog.show();
    }

    private void addItem() {
        String name = dialogAddItemEtItemName.getText().toString().trim();
        String des = dialogAddItemEtItemDes.getText().toString().trim();
        String price = dialogAddItemEtItemPrice.getText().toString().trim();
        String offer = dialogAddItemEtItemOffer.getText().toString().trim();
        getClient().getAddItem(convertToRequestBody(LoadData(getActivity(), "Restaurant_ApiToken")),
                convertToRequestBody(String.valueOf(categoryId)), convertToRequestBody(name),
                convertToRequestBody(des), convertToRequestBody(price),
                convertToRequestBody(offer), convertFileToMultipart(path, "photo")).enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {

            }
        });

    }

}