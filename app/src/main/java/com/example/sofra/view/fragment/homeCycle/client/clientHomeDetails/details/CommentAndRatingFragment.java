package com.example.sofra.view.fragment.homeCycle.client.clientHomeDetails.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.adapter.client.CommentandRatingAdapter;
import com.example.sofra.data.model.comment.Comment;
import com.example.sofra.data.model.comment.CommentData;
import com.example.sofra.helper.OnEndLess;
import com.example.sofra.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sofra.data.api.ApiClient.getClient;

public class CommentAndRatingFragment extends BaseFragment {

    public Integer id;
    @BindView(R.id.fragment_comment_rating_bt_add_comment)
    Button fragmentCommentRatingBtAddComment;
    @BindView(R.id.fragment_comment_rating_rv)
    RecyclerView fragmentCommentRatingRv;
    private Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private int maxPage;
    private OnEndLess onEndLess;
    private List<CommentData> commentDataList = new ArrayList<>();
    private CommentandRatingAdapter commentandRatingAdapter;

    public CommentAndRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_rating, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentCommentRatingRv.setLayoutManager(linearLayoutManager);
        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = onEndLess.current_page;
                        getComment(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                }

            }
        };
        fragmentCommentRatingRv.addOnScrollListener(onEndLess);
        commentandRatingAdapter = new CommentandRatingAdapter(getActivity(), commentDataList);
        fragmentCommentRatingRv.setAdapter(commentandRatingAdapter);
        getComment(1);
    }

    private void getComment(int page) {
        getClient().getComment(id, page).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        commentDataList.addAll(response.body().getData().getData());
                        commentandRatingAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

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

    @OnClick(R.id.fragment_comment_rating_bt_add_comment)
    public void onViewClicked() {
    }
}

