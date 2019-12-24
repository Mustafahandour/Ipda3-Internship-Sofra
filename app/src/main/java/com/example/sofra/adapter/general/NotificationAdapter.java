package com.example.sofra.adapter.general;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofra.R;
import com.example.sofra.data.model.notification.NotificationData;
import com.example.sofra.view.activity.BaseActivity;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public BaseActivity activity;

    private List<NotificationData> notificationDataList = new ArrayList<>();
    private Date date;

    public NotificationAdapter(Activity activity, List<NotificationData> notificationDataList) {
        this.activity = (BaseActivity) activity;
        this.notificationDataList = notificationDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_notification_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


    }

    private void setData(ViewHolder holder, int position) {
        if (notificationDataList.get(position).getAction().equals("new order")) {
            holder.itemNotificationIvIcon.setImageResource(R.drawable.ic_notification_solid);

        }else {
            holder.itemNotificationIvIcon.setImageResource(R.drawable.ic_notification);
        }
        holder.itemNotificationIvTitle.setText(notificationDataList.get(position).getTitle());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
       try {
           date = dateFormat.parse(notificationDataList.get(position).getCreatedAt());
       }catch (Exception e){

       }
        PrettyTime prettyTime = new PrettyTime();
       String time = prettyTime.format(date);
        holder.itemNotificationIvTime.setText(time);



    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_notification_iv_icon)
        ImageView itemNotificationIvIcon;
        @BindView(R.id.item_notification_iv_title)
        TextView itemNotificationIvTitle;
        @BindView(R.id.item_notification_iv_time)
        TextView itemNotificationIvTime;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
