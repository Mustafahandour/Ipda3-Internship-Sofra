package com.example.sofra.helper;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sofra.R;
import com.example.sofra.adapter.general.CustomSpinnerAdapter;
import com.example.sofra.data.model.regions.Region;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.helper.HelperMethod.replace;


public class GeneralRequest {

    public static void getSpinnerData(final Spinner spinner, final CustomSpinnerAdapter adapter, final String hint, Call<Region> method) {
        method.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {

                if (response.body().getStatus() == 1) {
                    adapter.setData(response.body().getData(), hint);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {


            }
        });
    }


    public static void getSpinnerData(final Spinner spinner, final CustomSpinnerAdapter adapter, final String hint, Call<Region> method, View view) {
        method.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {

                if (response.body().getStatus() == 1) {
                    if (view != null) {
                        view.setVisibility(View.VISIBLE);
                    }
                    adapter.setData(response.body().getData(), hint);
                    spinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {


            }
        });
    }

    public static void getSpinnerData(final Spinner spinner, final CustomSpinnerAdapter adapter,
                                      final String hint, Call<Region> method, AdapterView.OnItemSelectedListener listener) {
        method.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(listener);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });
    }


    /*
    - this 2 method for show spinner profile data
     */
    public static void getSpinnerData(final Spinner spinner, final CustomSpinnerAdapter adapter,
                                      final String hint, Call<Region> method, final int selectedId) {
        method.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        int position = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getId() == selectedId) {
                                position = i + 1;
                                break;
                            }
                        }
                        spinner.setSelection(position);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });
    }
    public static void getSpinnerData(final Spinner spinner, final CustomSpinnerAdapter adapter,
                                      final String hint, Call<Region> method, final int selectedId, AdapterView.OnItemSelectedListener listener) {
        method.enqueue(new Callback<Region>() {
            @Override
            public void onResponse(Call<Region> call, Response<Region> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        int position = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getId() == selectedId) {
                                position = i + 1;
                                break;
                            }
                        }
                        spinner.setSelection(position);
                        spinner.setOnItemSelectedListener(listener);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Region> call, Throwable t) {

            }
        });
    }

}



