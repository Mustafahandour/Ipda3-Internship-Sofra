package com.example.sofra.view.fragment.homeCycle.restaurant.restaurantMore;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.restaurant.RestaurantOffersAdapter;
import com.example.sofra.data.model.DateTxt;
import com.example.sofra.data.model.offers.Offers;
import com.example.sofra.data.model.offers.OffersData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.fragment.BaseFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Calendar;
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
import static com.example.sofra.helper.HelperMethod.showCalender;

public class RestaurantOffersFragment extends BaseFragment {

    @BindView(R.id.fragment_restaurant_offer_rv_offers)
    RecyclerView fragmentRestaurantOfferRvOffers;
    @BindView(R.id.fragment_restaurant_offers_bt_add_offers)
    Button fragmentRestaurantOffersBtAddOffers;
    TextView dialogOfferText;


    private Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private int maxPage;
    private OnEndLess onEndLess;
    private List<OffersData> offersDataList = new ArrayList<>();
    private RestaurantOffersAdapter restaurantOffersAdapter;
    private ArrayList<AlbumFile> image = new ArrayList<>();
    private String path;
    private EditText dialogAddOfferEtOfferName, dialogAddOfferEtOfferDes, dialogAddOfferEtPrice, dialogAddOfferEtOfferPrice;
    private TextView dialogAddOfferTvDateFrom, dialogAddOfferTvDateTo;

    public RestaurantOffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers_restaurant, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();

        return view;
    }

    private void getData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentRestaurantOfferRvOffers.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getOffers(current_page);

                    }

                }
            }
        };
        fragmentRestaurantOfferRvOffers.addOnScrollListener(onEndLess);
        restaurantOffersAdapter = new RestaurantOffersAdapter(getActivity(), offersDataList);
        fragmentRestaurantOfferRvOffers.setAdapter(restaurantOffersAdapter);
        getOffers(1);

    }

    private void getOffers(int page) {
        getClient().getRestaurantOffers(LoadData(getActivity(), "Restaurant_ApiToken"), page).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        offersDataList.addAll(response.body().getData().getData());
                        restaurantOffersAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

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

    @OnClick(R.id.fragment_restaurant_offers_bt_add_offers)
    public void onViewClicked() {
        openDialog();
    }

    private void openDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_offers);
        CircleImageView dialogAddOfferCiImage = dialog.findViewById(R.id.dialog_add_offer_ci_image);
        dialogOfferText = dialog.findViewById(R.id.dialog_offer_text);
        dialogOfferText.setText(getString(R.string.add_offers));
        dialogAddOfferCiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(getActivity(), 1, image, new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        image.clear();
                        path = result.get(0).getPath();
                        onLoadImageFromUrl(dialogAddOfferCiImage, path, getActivity());

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
                showCalender(getActivity(), null, dialogAddOfferTvDateFrom, dateTxt);

            }
        });


        dialogAddOfferTvDateTo = dialog.findViewById(R.id.dialog_add_offer_tv_date_to);
        dialogAddOfferTvDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalender(getActivity(), null, dialogAddOfferTvDateTo, dateTxt);

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
                addOffers();

            }
        });

        dialog.show();

    }

    private void addOffers() {
        String name = dialogAddOfferEtOfferName.getText().toString().trim();
        String des = dialogAddOfferEtOfferDes.getText().toString().trim();
        String price = dialogAddOfferEtPrice.getText().toString().trim();
        String offerPrice = dialogAddOfferEtOfferPrice.getText().toString().trim();
        String fromDate = dialogAddOfferTvDateFrom.getText().toString().trim();
        String toDate = dialogAddOfferTvDateTo.getText().toString().trim();
        getClient().getNewRestaurantOffers(convertToRequestBody(name), convertToRequestBody(des),
                convertToRequestBody(price), convertToRequestBody(offerPrice), convertToRequestBody(fromDate),
                convertToRequestBody(toDate), convertToRequestBody(LoadData(getActivity(), "Restaurant_ApiToken")),
                convertFileToMultipart(path, "photo")).enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        restaurantOffersAdapter.notifyDataSetChanged();

                    }
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });
    }
}

