package com.jnanatech.mochwo.notification.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.notification.model.NotificationModel;

import java.util.ArrayList;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<NotificationModel> notificationModels;

    public NotificationRecyclerViewAdapter(Context context, ArrayList<NotificationModel> notificationModels) {
        this.context = context;
        this.notificationModels = notificationModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NotificationModel currentNotification = notificationModels.get(position);

        holder.date.setText(currentNotification.getTitle());
        holder.description.setText(currentNotification.getDetail() );
        holder.about.setText("Notification About " );


    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView about;
        TextView description;
        TextView date;
        LinearLayout mainView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            about = (TextView) itemView.findViewById(R.id.notificationAbout);
            description = (TextView) itemView.findViewById(R.id.notificationDescription);
            date = (TextView) itemView.findViewById(R.id.notificationDate);
            mainView = (LinearLayout) itemView.findViewById(R.id.mainView);
        }
    }
}
