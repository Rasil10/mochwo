package com.jnanatech.mochwo.schedule.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.scheduleDetail.ScheduleDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<EventModel> eventModels;
    private ArrayList<EventModel> eventModelsFiletered = new ArrayList<>();
    ArrayList<SpeakerModel> speakerModels;


    public EventAdapter(Context context, ArrayList<EventModel> eventModels, ArrayList<SpeakerModel> speakerModels) {
        this.context = context;
        this.eventModels = eventModels;
        this.eventModelsFiletered = eventModels;
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
        final EventModel currentEvent = eventModelsFiletered.get(position);

        holder.eventStartingTime.setText(currentEvent.getStartTime());
        holder.eventEndingTime.setText(currentEvent.getEndTime());
        holder.eventTitle.setText(currentEvent.getEventTitle());
        holder.eventSpeakers.setText("Chair   "+currentEvent.getChairPersonName());


        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ScheduleDetailActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString(Constants.scheduleStartTimeConstant, currentEvent.getStartTime());
                bundle.putString(Constants.scheduleEndTimeConstant, currentEvent.getEndTime());
                bundle.putString(Constants.scheduleTopicConstant, currentEvent.getEventTitle());
                bundle.putString(Constants.scheduleDescriptionConstant, currentEvent.getAbstractDetail());
                bundle.putString(Constants.scheduleKeywordsConstant, currentEvent.getKeywords());
                bundle.putString(Constants.scheduleSpeakersConstant, currentEvent.getEventSpeaker());
                bundle.putString(Constants.scheduleIDConstant, currentEvent.getId());
                bundle.putString(Constants.scheduleScheduleConstant, currentEvent.getScheduleName());
                bundle.putString(Constants.scheduleSessionConstant, currentEvent.getSessionTitle());
                bundle.putBoolean(Constants.scheduleBookmarkConstant, currentEvent.isBookmarked());

                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventModelsFiletered.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                Log.d("searchTest", charSequence.toString());
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    eventModelsFiletered = eventModels;
//                } else {
//                    ArrayList<EventModel> filteredList = new ArrayList<>();
//                    for (EventModel row : eventModels) {
//
//                        if ((row.getSessionTitle()).contains(charString.toLowerCase())
//                                || ((row.getKeywords()).toLowerCase()).contains(charString.toLowerCase())
////                                || ((row.getExperience2()).toLowerCase()).contains(charString.toLowerCase())
////                                || ((row.getExperience3()).toLowerCase()).contains(charString.toLowerCase())
////                                || ((row.getExperience4()).toLowerCase()).contains(charString.toLowerCase())
//                        ) {
//
//                            filteredList.add(row);
//                        }
//                    }
//
//                    eventModelsFiletered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = eventModelsFiletered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                eventModelsFiletered = (ArrayList<EventModel>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

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
