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
import com.example.sofra.adapter.restaurant.RestaurantHomeAdapter;
import com.example.sofra.data.model.category.Category;
import com.example.sofra.data.model.category.CategoryData;
import com.example.sofra.helper.OnEndLess;
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

public class RestaurantHomeFragment extends BaseFragment {


    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.fragment_restaurant_home_title)
    TextView fragmentRestaurantHomeTitle;
    @BindView(R.id.fragment_restaurant_home_rv_category)
    RecyclerView fragmentRestaurantHomeRvCategory;
    @BindView(R.id.fragment_restaurant_home_ib_add_category)
    ImageButton fragmentRestaurantHomeIbAddCategory;
    public List<CategoryData> categoryDataList = new ArrayList<>();

    private Unbinder unbinder;
    public String email;
    public String password;
    private RestaurantHomeAdapter restaurantCategoryAdapter;
    private EditText dialogAddCategoryEtCategoryName;
    private CircleImageView dialogAddCategoryCiImage;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;
    private int maxPage;
    private OnEndLess onEndLess;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        initFragment();
        unbinder = ButterKnife.bind(this, view);


        getRestaurantData();


        return view;
    }

    private void getRestaurantData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentRestaurantHomeRvCategory.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager,1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getRestaurantHomeData(current_page);

                    }else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
                }
            }
        };
        fragmentRestaurantHomeRvCategory.addOnScrollListener(onEndLess);
        restaurantCategoryAdapter = new RestaurantHomeAdapter(getActivity(), categoryDataList);
        fragmentRestaurantHomeRvCategory.setAdapter(restaurantCategoryAdapter);
        getRestaurantHomeData(1);

    }


    private void getRestaurantHomeData(int page) {
        getClient().getRestaurantCategory(LoadData(getActivity(),"Restaurant_ApiToken"),page).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.body().getStatus() == 1) {
                    maxPage = response.body().getData().getLastPage();
                    categoryDataList.addAll(response.body().getData().getData());

                    restaurantCategoryAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.fragment_restaurant_home_ib_add_category)
    public void onViewClicked() {
        openDialog();
    }


    private void openDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_category);



         dialogAddCategoryEtCategoryName = dialog.findViewById(R.id.dialog_add_category_et_category_name);
        // set the custom dialog components  image clicked
        dialogAddCategoryCiImage = dialog.findViewById(R.id.dialog_add_category_ci_image);
        dialogAddCategoryCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(getActivity(), 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogAddCategoryCiImage,path,getActivity());
                    }
                });

            }
        });




        // if button is clicked
        Button dialogAddCategoryBtAdd = dialog.findViewById(R.id.dialog_add_category_bt_add);
        dialogAddCategoryBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                addCategory();

            }
        });
        dialog.show();
    }

    private void addCategory() {
        String name = dialogAddCategoryEtCategoryName.getText().toString().trim();
        getClient().getAddCategory(convertToRequestBody(name),convertFileToMultipart(path,"photo"),convertToRequestBody(LoadData(getActivity(),"Restaurant_ApiToken"))).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    restaurantCategoryAdapter.notifyDataSetChanged();
                    RestaurantHomeFragment restaurantHomeFragment = new RestaurantHomeFragment();
                    replace(restaurantHomeFragment,getActivity().getSupportFragmentManager(),R.id.nav_host_fragment);
                }
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBack() {
        getActivity().finish();
    }
}