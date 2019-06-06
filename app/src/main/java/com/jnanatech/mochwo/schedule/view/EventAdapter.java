package com.jnanatech.mochwo.schedule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.scheduleDetail.ScheduleDetailActivity;
import com.jnanatech.mochwo.speakerDetail.view.SpeakerDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<EventModel> eventModels;

    ArrayList<SpeakerModel> speakerModels;


    public EventAdapter(Context context, ArrayList<EventModel> eventModels, ArrayList<SpeakerModel> speakerModels) {
        this.context = context;
        this.eventModels = eventModels;
        this.speakerModels = speakerModels;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final EventModel currentEvent = eventModels.get(position);

        holder.eventStartingTime.setText(currentEvent.getStartTime());
        holder.eventEndingTime.setText(currentEvent.getEndTime());
        holder.eventTitle.setText(currentEvent.getTopic());
        holder.eventSpeakers.setText(currentEvent.getSpeakers());


        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ScheduleDetailActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString(Constants.scheduleStartTimeConstant, currentEvent.getStartTime());
                bundle.putString(Constants.scheduleEndTimeConstant, currentEvent.getEndTime());
                bundle.putString(Constants.scheduleTopicConstant, currentEvent.getTopic());
                bundle.putString(Constants.scheduleDescriptionConstant, currentEvent.getDescription());
                bundle.putString(Constants.scheduleKeywordsConstant, currentEvent.getKeywords());
                bundle.putString(Constants.scheduleSpeakersConstant, currentEvent.getSpeakers());
                bundle.putString(Constants.scheduleIDConstant,currentEvent.getId());
                bundle.putString(Constants.scheduleScheduleConstant,currentEvent.getScheduleName());
                bundle.putString(Constants.scheduleSessionConstant,currentEvent.getSessionName());
                bundle.putBoolean(Constants.scheduleBookmarkConstant,currentEvent.isBookmarked());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView mainlayout;
        TextView eventStartingTime;
        TextView eventEndingTime;
        TextView eventTitle;
        TextView eventSpeakers;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainlayout = (CardView) itemView.findViewById(R.id.mainlayout);
            eventStartingTime = (TextView) itemView.findViewById(R.id.eventStartingTime);
            eventEndingTime = (TextView) itemView.findViewById(R.id.eventEndingTime);
            eventTitle = (TextView) itemView.findViewById(R.id.eventTitle);
            eventSpeakers = (TextView) itemView.findViewById(R.id.eventSpeakers);
        }
    }
}
