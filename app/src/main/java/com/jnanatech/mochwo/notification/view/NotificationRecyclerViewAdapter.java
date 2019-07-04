package com.jnanatech.mochwo.notification.view;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.schedule.view.ScheduleActivity;
import com.jnanatech.mochwo.speakers.view.SpeakersActivity;
import com.jnanatech.mochwo.sponsers.view.SponserActivity;

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
        final NotificationModel currentNotification = notificationModels.get(position);

        Log.d("newsCheck",currentNotification.getCategory());

        holder.title.setText(currentNotification.getTitle());
        holder.description.setText(Html.fromHtml(currentNotification.getDetail()));

        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (currentNotification.getCategory()) {
                    case "speakers":
                        context.startActivity(new Intent(context, SpeakersActivity.class));
                        break;
                    case "sponsors":
                        context.startActivity(new Intent(context, SponserActivity.class));
                        break;
                    case "schedules":
                        context.startActivity(new Intent(context, ScheduleActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        LinearLayout mainView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notificationTitle);
            description = (TextView) itemView.findViewById(R.id.notificationDetail);
//            category = (TextView) itemView.findViewById(R.id.notificationCategory);
            mainView = (LinearLayout) itemView.findViewById(R.id.mainView);
        }
    }
}
