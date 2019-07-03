package com.jnanatech.mochwo.schedule.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.scheduleDetail.ScheduleDetailActivity;
import com.jnanatech.mochwo.utils.Constants;

import java.util.ArrayList;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<EventModel> innerEvents;

    public InnerRecyclerViewAdapter(Context context, ArrayList<EventModel> innerEvents) {
        this.context = context;
        this.innerEvents = innerEvents;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inner_event, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final EventModel currentEvent = innerEvents.get(position);
        holder.title.setText((position + 1) + " " + innerEvents.get(position).getEventTitle());
        holder.speakers.setText(innerEvents.get(position).getEventSpeaker());
        holder.eventLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScheduleDetailActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString(Constants.scheduleIDConstant, currentEvent.getId());
                bundle.putString(Constants.scheduleStartTimeConstant, currentEvent.getStartTime());
                bundle.putString(Constants.scheduleEndTimeConstant, currentEvent.getEndTime());
                bundle.putString(Constants.scheduleChairPersonConstant, currentEvent.getChairPersonName());
                bundle.putString(Constants.scheduleChairPersonDetailConstant, currentEvent.getChairPersonDetail());

                bundle.putString(Constants.scheduleTopicConstant, currentEvent.getEventTitle());
                bundle.putString(Constants.scheduleSpeakersConstant, currentEvent.getEventSpeaker());
                bundle.putString(Constants.scheduleSpeakersDetailConstant, currentEvent.getSpeakerDetail());
                bundle.putString(Constants.scheduleDescriptionConstant, currentEvent.getAbstractDetail());
                bundle.putString(Constants.scheduleKeywordsConstant, currentEvent.getKeywords());

                bundle.putString(Constants.scheduleScheduleConstant, currentEvent.getScheduleName());
                bundle.putString(Constants.scheduleSessionConstant, currentEvent.getSessionTitle());
                bundle.putBoolean(Constants.scheduleBookmarkConstant, currentEvent.isBookmarked());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return innerEvents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView speakers;
        LinearLayout eventLL;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.eventTitle);
            speakers = (TextView) itemView.findViewById(R.id.eventSpeakers);
            eventLL = (LinearLayout) itemView.findViewById(R.id.eventLL);
        }
    }
}
