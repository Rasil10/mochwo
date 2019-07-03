package com.jnanatech.mochwo.schedule.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;

import java.util.ArrayList;


public class ExpandableEventAdapter extends RecyclerView.Adapter<ExpandableEventAdapter.ExpandableViewHolder> {
    Context context;
    ArrayList<EventModel> eventModels;

    public ExpandableEventAdapter(Context context, ArrayList<EventModel> eventModels) {
        this.context = context;
        this.eventModels = eventModels;
    }

    @NonNull
    @Override
    public ExpandableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ExpandableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpandableViewHolder holder, int position) {

        final EventModel currentEvent = eventModels.get(position);

        if (position > 0) {
            //case for other position (Except 0) no duplicate title
            if (currentEvent.getSessionTitle().equals(eventModels.get(position - 1).getSessionTitle())) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            } else {

                holder.eventStartingTime.setText(currentEvent.getStartTime());
                holder.eventEndingTime.setText(currentEvent.getEndTime());
                holder.eventTitle.setText(currentEvent.getSessionTitle());
                holder.eventSpeakers.setText("Chair   " + currentEvent.getChairPersonName());


                InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(context, getEventBySessionName(currentEvent.getSessionTitle()));
                holder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                holder.mainlayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.innerRecyclerView.getVisibility() == View.VISIBLE) {
                            holder.innerRecyclerView.setVisibility(View.GONE);

                        } else {
                            holder.innerRecyclerView.setVisibility(View.VISIBLE);

                        }
                    }
                });
                holder.innerRecyclerView.setAdapter(itemInnerRecyclerView);
            }
        } else {

            //case for position 0
            holder.eventStartingTime.setText(currentEvent.getStartTime());
            holder.eventEndingTime.setText(currentEvent.getEndTime());
            holder.eventTitle.setText(currentEvent.getSessionTitle());
            holder.eventSpeakers.setText("Chair   " + currentEvent.getChairPersonName());


            InnerRecyclerViewAdapter itemInnerRecyclerView = new InnerRecyclerViewAdapter(context, getEventBySessionName(currentEvent.getSessionTitle()));
            holder.innerRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            holder.mainlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.innerRecyclerView.getVisibility() == View.VISIBLE) {
                        holder.innerRecyclerView.setVisibility(View.GONE);

                    } else {
                        holder.innerRecyclerView.setVisibility(View.VISIBLE);

                    }
                }
            });
            holder.innerRecyclerView.setAdapter(itemInnerRecyclerView);
        }


    }


    public ArrayList<EventModel> getEventBySessionName(String sessionName) {

        ArrayList<EventModel> list = new ArrayList<>();
        for (int i = 0; i < eventModels.size(); i++) {
            if (eventModels.get(i).getSessionTitle().equals(sessionName)) {
                list.add(eventModels.get(i));
            }
        }
        return list;
    }


    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    private int getSize() {
        int count = 1;
        for (int i = 0; i < eventModels.size(); i++) {
            if (i != 0) {
                if (!(eventModels.get(i).getSessionTitle().equals(eventModels.get(i - 1).getSessionTitle()))) {
                    count = count + 1;
                }
            }
        }
        return count + 1;
    }

    public class ExpandableViewHolder extends RecyclerView.ViewHolder {
        CardView mainlayout;
        TextView eventStartingTime;
        TextView eventEndingTime;
        TextView eventTitle;
        TextView eventSpeakers;
        RecyclerView innerRecyclerView;

        public ExpandableViewHolder(@NonNull View itemView) {
            super(itemView);
            mainlayout = (CardView) itemView.findViewById(R.id.mainlayout);
            eventStartingTime = (TextView) itemView.findViewById(R.id.eventStartingTime);
            eventEndingTime = (TextView) itemView.findViewById(R.id.eventEndingTime);
            eventTitle = (TextView) itemView.findViewById(R.id.eventTitle);
            eventSpeakers = (TextView) itemView.findViewById(R.id.eventSpeakers);
            innerRecyclerView = (RecyclerView) itemView.findViewById(R.id.innerRecyclerView);
        }
    }


}
