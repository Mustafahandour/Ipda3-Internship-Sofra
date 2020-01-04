package com.example.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.comment.CommentData;
import com.example.sofra.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentandRatingAdapter extends RecyclerView.Adapter<CommentandRatingAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<CommentData> commentDataList = new ArrayList<>();

    public CommentandRatingAdapter(Activity activity, List<CommentData> commentDataList) {
        this.activity = (BaseActivity) activity;
        this.commentDataList = commentDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_comment_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        holder.itemCommentListName.setText(commentDataList.get(position).getClient().getName());
        holder.itemCommentListComment.setText(commentDataList.get(position).getComment());
        if (commentDataList.get(position).getRate().equals(1)) {
            holder.itemCommentListRate.setImageResource(R.drawable.ic_very_bad_rate);

        }else if (commentDataList.get(position).getRate().equals(2)){
            holder.itemCommentListRate.setImageResource(R.drawable.ic_bad_rate);

        }else if (commentDataList.get(position).getRate().equals(3)){
            holder.itemCommentListRate.setImageResource(R.drawable.ic_good_rate);

        }else if (commentDataList.get(position).getRate().equals(4)){
            holder.itemCommentListRate.setImageResource(R.drawable.ic_very_good_rate);

        }else {
            holder.itemCommentListRate.setImageResource(R.drawable.ic_excellent_rate);

        }


    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return commentDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_comment_list_name)
        TextView itemCommentListName;
        @BindView(R.id.item_comment_list_comment)
        TextView itemCommentListComment;
        @BindView(R.id.item_comment_list_rate)
        ImageView itemCommentListRate;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
